package com.example.paginglibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.paginglibrary.datasource.DataSourceFactory
import com.example.paginglibrary.datasource.ItemDataSource
import com.example.paginglibrary.model.UserModel

/**
 * @author Yogesh Paliyal
 * techpaliyal@gmail.com
 * http://techpaliyal.com
 */
class MainActivityViewModel() : ViewModel(){
    var dataList: LiveData<PagedList<UserModel>>? = null
    var liveDataSource: LiveData<PageKeyedDataSource<Int, UserModel>>? = null

    init {
        //getting our data source factory

        //getting our data source factory
        val itemDataSourceFactory = DataSourceFactory()

        //getting the live data source from data source factory

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource()


        //Getting PagedList config

        //Getting PagedList config
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ItemDataSource.PAGE_SIZE).build()

        //Building the paged list

        //Building the paged list
        dataList = LivePagedListBuilder(itemDataSourceFactory, pagedListConfig).build()
    }
}