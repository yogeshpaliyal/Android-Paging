package com.example.paginglibrary.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserModel {
    @SerializedName("name")
    @Expose
    var name = ""
}