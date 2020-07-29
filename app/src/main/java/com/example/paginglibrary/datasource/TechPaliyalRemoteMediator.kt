package com.example.paginglibrary.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.paginglibrary.model.UserModel


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

    }

}