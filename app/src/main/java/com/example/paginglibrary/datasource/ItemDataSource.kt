package com.example.paginglibrary.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.paginglibrary.callers.UserCaller
import com.example.paginglibrary.model.BaseApiModel
import com.example.paginglibrary.model.UserListResponse
import com.example.paginglibrary.model.UserModel
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ItemDataSource : PageKeyedDataSource<Int, UserModel>(){
    //the size of a page that we want
    companion object {
        val PAGE_SIZE = 5
    }
    //we will start from the first page which is 1
    private val FIRST_PAGE = 1


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UserModel>
    ) {
        UserCaller().getUsers(FIRST_PAGE, PAGE_SIZE).
                subscribe(object : Observer<BaseApiModel>{
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseApiModel) {
                        val userListResponse = Gson().fromJson(t.data, UserListResponse::class.java)
                        callback.onResult(userListResponse.getUser(), null, userListResponse.getNextPageNo())
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserModel>) {
        UserCaller().getUsers(params.key, params.requestedLoadSize).
        subscribe(object : Observer<BaseApiModel>{
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: BaseApiModel) {
                val userListResponse = Gson().fromJson(t.data, UserListResponse::class.java)
                callback.onResult(userListResponse.getUser(),userListResponse.getNextPageNo())
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UserModel>) {
        UserCaller().getUsers(params.key, params.requestedLoadSize).
        subscribe(object : Observer<BaseApiModel>{
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: BaseApiModel) {
                val userListResponse = Gson().fromJson(t.data, UserListResponse::class.java)
                callback.onResult(userListResponse.getUser(), userListResponse.getLastPageNo() )
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

        })
    }

}