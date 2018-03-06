package com.example.roy.habittrainer.db

import android.provider.BaseColumns

/**
 * Created by Roy on 3/6/18.
 */

val DATABASE_NAME = "habitsTrainer.db"
val DATABASE_VERSION = 10

object HabitEntry : BaseColumns {
    val TABLE_NAME = "habit"
    val _ID = "id"
    val TITLE_COLUMN = "title"
    val DESCRIPTION_COLUMN = "description"
    val IMAGE_COLUMN = "image"
}
