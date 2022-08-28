package com.programmingsimplified.testinginandroid.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val title:String="",
    val description:String=""
)
