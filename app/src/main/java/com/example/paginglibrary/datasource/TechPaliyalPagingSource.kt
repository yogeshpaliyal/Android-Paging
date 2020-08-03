package com.example.paginglibrary.datasource

import androidx.paging.PagingSource
import com.androidnetworking.AndroidNetworking
import com.example.paginglibrary.callers.UserCaller
import com.example.paginglibrary.constants.Apis
import com.example.paginglibrary.model.BaseApiModel
import com.example.paginglibrary.model.UserListResponse
import com.example.paginglibrary.model.UserModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher


/*
* @author Yogesh Paliyal
* techpaliyal@gmail.com
* https://techpaliyal.com
* created on 26-07-2020 11:19
*/

class TechPaliyalPagingSource : PagingSource<Int, UserModel>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel> {
        return withContext(Dispatchers.IO) {
            val response = AndroidNetworking.get(Apis.USERS)
                .addQueryParameter("page", (params.key ?: 1).toString())
                .addQueryParameter("page_size", Apis.PAGE_SIZE.toString())
                .build().executeForObject(BaseApiModel::class.java)
            if (response.isSuccess) {
                val baseApiResponse = response.result as BaseApiModel
                if (baseApiResponse.status == 200) {
                    // success
                    val userListResponse =
                        Gson().fromJson(baseApiResponse.data, UserListResponse::class.java)
                   return@withContext LoadResult.Page<Int, UserModel>(
                        userListResponse.getUser(),
                        userListResponse.getLastPageNo(),
                        userListResponse.getNextPageNo()
                    )
                }
            }
            LoadResult.Error<Int, UserModel>(Throwable("Some error occurred"))
        }

    }

}