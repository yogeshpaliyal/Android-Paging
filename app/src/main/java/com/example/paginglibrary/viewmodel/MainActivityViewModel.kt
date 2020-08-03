package com.example.paginglibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.observable
import com.example.paginglibrary.constants.Apis
import com.example.paginglibrary.datasource.TechPaliyalPagingSource
import com.example.paginglibrary.datasource.TechPaliyalRemoteMediator
import com.example.paginglibrary.model.UserModel
import com.example.paginglibrary.room.AppDatabase
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

/**
 * @author Yogesh Paliyal
 * techpaliyal@gmail.com
 * http://techpaliyal.com
 */
class MainActivityViewModel() : ViewModel(){

    fun hitApi(): Flow<PagingData<UserModel>>?{
        //getting the live data source from data source factory
        val newData =   Pager(
            config = PagingConfig(
                pageSize = Apis.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator =  TechPaliyalRemoteMediator(AppDatabase.getInstance()),
            pagingSourceFactory = { AppDatabase.getInstance().userDao().concertsByDate() }

        ).flow.cachedIn(viewModelScope)
        return newData
    }

}