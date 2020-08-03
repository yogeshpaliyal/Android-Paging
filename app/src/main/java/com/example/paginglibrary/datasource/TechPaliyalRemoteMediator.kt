package com.example.paginglibrary.datasource

import androidx.paging.*
import androidx.room.withTransaction
import com.androidnetworking.AndroidNetworking
import com.example.paginglibrary.constants.Apis
import com.example.paginglibrary.model.BaseApiModel
import com.example.paginglibrary.model.RemoteKeys
import com.example.paginglibrary.model.UserListResponse
import com.example.paginglibrary.model.UserModel
import com.example.paginglibrary.room.AppDatabase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InvalidObjectException


/*
* @author Yogesh Paliyal
* techpaliyal@gmail.com
* https://techpaliyal.com
* created on 26-07-2020 20:11
*/

@OptIn(ExperimentalPagingApi::class)
class TechPaliyalRemoteMediator(val repoDatabase: AppDatabase) : RemoteMediator<Int, UserModel>(){
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserModel>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }


         return withContext(Dispatchers.IO) {
            val response = AndroidNetworking.get(Apis.USERS)
                .addQueryParameter("page", (page ?: 1).toString())
                .addQueryParameter("page_size", Apis.PAGE_SIZE.toString())
                .build().executeForObject(BaseApiModel::class.java)
            if (response.isSuccess) {
                val baseApiResponse = response.result as BaseApiModel
                if (baseApiResponse.status == 200) {
                    // success
                    val userListResponse =
                        Gson().fromJson(baseApiResponse.data, UserListResponse::class.java)

                    repoDatabase.withTransaction {
                        // clear all tables in the database
                        if (loadType == LoadType.REFRESH) {
                            AppDatabase.getInstance().remoteKeysDao().clearRemoteKeys()
                            AppDatabase.getInstance().userDao().deleteAll()
                        }
                        val prevKey = userListResponse.getLastPageNo()
                        val nextKey = userListResponse.getNextPageNo()
                        val keys = userListResponse.getUser().map {
                            RemoteKeys(userId = it.id!!, prevKey = prevKey, nextKey = nextKey)
                        }
                        AppDatabase.getInstance().remoteKeysDao().insertAll(keys)
                        AppDatabase.getInstance().userDao().insertAll(userListResponse.getUser())
                    }
                    return@withContext MediatorResult.Success(endOfPaginationReached = userListResponse.getNextPageNo() == null)
                }
            }
            return@withContext MediatorResult.Error(Throwable("Some error occurred"))
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UserModel>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                repoDatabase.remoteKeysDao().remoteKeysRepoId(repo.id!!)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UserModel>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                repoDatabase.remoteKeysDao().remoteKeysRepoId(repo.id!!)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UserModel>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                repoDatabase.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }

}