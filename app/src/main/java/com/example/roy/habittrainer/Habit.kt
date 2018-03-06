package com.example.roy.habittrainer

import android.graphics.Bitmap

/**
 * Created by Roy on 3/5/18.
 */

data class Habit(val title: String, val description: String, val image: Bitmap)

//fun getSampleHabits(): List<Habit> {
//    return listOf(
//            Habit("Go for a walk",
//                    "A nice walk in the sun gets you ready for the day ahead",
//                    R.drawable.walk),
//            Habit("Drink a Glass of Water",
//                    "A Refreshing glass of water keeps you hydrated",
//                    R.drawable.water)
//    )

//}