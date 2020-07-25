package com.example.paginglibrary.callers

import com.example.paginglibrary.Apis
import com.example.paginglibrary.model.BaseApiModel
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class UserCaller {
     fun  getUsers(page: Int, pageSize: Int): Observable<BaseApiModel>{
        return Rx2AndroidNetworking.get(Apis.USERS)
            .addQueryParameter("page",page.toString())
            .addQueryParameter("page_size",pageSize.toString())
            .build()
            .getObjectObservable(BaseApiModel::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }
}