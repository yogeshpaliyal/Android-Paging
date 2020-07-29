package com.example.paginglibrary.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * @author Yogesh Paliyal
 * techpaliyal@gmail.com
 * http://techpaliyal.com
 */

open class PaginationBaseModel {
    @SerializedName("current_page")
    @Expose
    var current_page = 0

    @SerializedName("total_page")
    @Expose
    var total_page = 0

    fun getNextPageNo() : Int?{
        return if (current_page < total_page)
            current_page+1
        else
            null
    }

    fun getLastPageNo() : Int?{
        return if (current_page > 1)
            current_page-1
        else
            null
    }
}