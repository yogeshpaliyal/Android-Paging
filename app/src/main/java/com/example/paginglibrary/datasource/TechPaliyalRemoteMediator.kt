package com.example.paginglibrary.datasource

import androidx.paging.*
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
class TechPaliyalRemoteMediator : RemoteMediator<Int, UserModel>(){
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserModel>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = false)
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
                .addQueryParameter("page", page.toString())
                .addQueryParameter("page_size", state.config.pageSize.toString())
                .build().executeForObject(BaseApiModel::class.java)
            if (response.isSuccess) {
                val baseApiResponse = response.result as BaseApiModel
                if (baseApiResponse.status == 200) {
                    // success
                    val userListResponse =
                        Gson().fromJson(baseApiResponse.data, UserListResponse::class.java)


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

                    return@withContext MediatorResult.Success(endOfPaginationReached = false)
                }
            }
            return@withContext MediatorResult.Error(Throwable("Some error occurred"))
        }
    }

}