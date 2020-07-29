package com.example.paginglibrary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * @author Yogesh Paliyal
 * techpaliyal@gmail.com
 * http://techpaliyal.com
 */
@Entity(tableName = "users")
class UserModel {
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    var id : Int ?= null

    @SerializedName("name")
    @ColumnInfo(name = "name")
    @Expose
    var name = ""
}