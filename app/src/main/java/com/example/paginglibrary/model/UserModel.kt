package com.example.paginglibrary.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * @author Yogesh Paliyal
 * techpaliyal@gmail.com
 * http://techpaliyal.com
 */
class UserModel {
    @SerializedName("name")
    @Expose
    var name = ""
}