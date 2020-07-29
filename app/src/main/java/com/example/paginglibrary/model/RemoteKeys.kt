package com.example.paginglibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey


/*
* @author Yogesh Paliyal
* techpaliyal@gmail.com
* https://techpaliyal.com
* created on 29-07-2020 09:05
*/

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val userId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)