package com.example.paginglibrary.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * @author Yogesh Paliyal
 * techpaliyal@gmail.com
 * http://techpaliyal.com
 */
class UserListResponse : PaginationBaseModel() {
    @SerializedName("users")
    @Expose
    private var users: List<UserModel>? = null

    fun getUser(): List<UserModel>{
        return if (users.isNullOrEmpty())
            ArrayList<UserModel>()
        else
            users!!
    }
}