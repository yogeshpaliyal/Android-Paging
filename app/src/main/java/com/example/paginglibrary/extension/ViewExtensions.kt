package com.example.paginglibrary.extension

import android.view.View


/*
* @author Yogesh Paliyal
* techpaliyal@gmail.com
* https://techpaliyal.com
* created on 26-07-2020 12:09
*/

fun View.isVisible(boolean: Boolean){
    if (boolean)
        visibility = View.VISIBLE
    else
        visibility = View.GONE
}