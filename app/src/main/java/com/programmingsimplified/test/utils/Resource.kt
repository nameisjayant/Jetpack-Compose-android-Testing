package com.programmingsimplified.testinginandroid.utils

sealed class Resource<out T>{

    data class Success<out R>(val data:R) : Resource<R>()
    data class Failure(val msg:String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    object Empty : Resource<Nothing>()

}
