package com.example.paginglibrary.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.paginglibrary.model.RemoteKeys
import com.example.paginglibrary.model.UserModel
import com.example.paginglibrary.room.dao.RemoteKeysDao
import com.example.paginglibrary.room.dao.UserDao


/*
* @author Yogesh Paliyal
* techpaliyal@gmail.com
* https://techpaliyal.com
* created on 26-07-2020 10:37
*/

@Database(entities = arrayOf(UserModel::class, RemoteKeys::class), version = 2)
abstract class AppDatabase() : RoomDatabase() {


    abstract fun userDao(): UserDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object{
        private lateinit var instance : AppDatabase
        fun createInstance(mContext: Context){
            instance = Room.databaseBuilder(
                mContext.applicationContext,
                AppDatabase::class.java, "database-name"
            ).fallbackToDestructiveMigration()
                .fallbackToDestructiveMigrationOnDowngrade().build()
        }

        fun getInstance(): AppDatabase{
            return instance
        }
    }
}