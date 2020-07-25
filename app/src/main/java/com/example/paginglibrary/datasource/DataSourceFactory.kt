package com.example.paginglibrary.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.paginglibrary.model.UserModel

/**
 * @author Yogesh Paliyal
 * techpaliyal@gmail.com
 * http://techpaliyal.com
 */
class DataSourceFactory : DataSource.Factory<Int, UserModel>(){

    //creating the mutable live data
    private val itemLiveDataSource =
        MutableLiveData<PageKeyedDataSource<Int, UserModel>>()

    override fun create(): DataSource<Int, UserModel> {
        //getting our data source object

        //getting our data source object
        val itemDataSource = ItemDataSource()

        //posting the datasource to get the values

        //posting the datasource to get the values
        itemLiveDataSource.postValue(itemDataSource)

        //returning the datasource

        //returning the datasource
        return itemDataSource
    }

    fun getItemLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, UserModel>>{
        return itemLiveDataSource
    }

}