package com.example.paginglibrary.room.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.paginglibrary.model.UserModel


/*
* @author Yogesh Paliyal
* techpaliyal@gmail.com
* https://techpaliyal.com
* created on 26-07-2020 10:38
*/

@Dao
abstract class UserDao {
    @Query("SELECT * FROM users")
    abstract suspend fun getAll(): List<UserModel>

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    abstract suspend fun loadAllByIds(userIds: IntArray): List<UserModel>

   /* @Query("SELECT * FROM users WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    abstract fun findByName(first: String, last: String): UserModel*/

    @Insert
    abstract suspend fun insertAll(users: List<UserModel>)

    @Delete
    abstract suspend fun delete(user: UserModel)

    @Query("DELETE FROM users")
    abstract suspend fun deleteAll()

    // The Int type parameter tells Room to use a PositionalDataSource object.
    @Query("SELECT * FROM users")
    public abstract suspend fun concertsByDate(): DataSource.Factory<Int, UserModel>
}