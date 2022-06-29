package com.borisov.movies.utils

import android.graphics.Color

/**
 * @author Borisov Andrey on 28.06.2022
 **/
fun getColorByValue(value: Int): Int =
    when (value) {
        in 0..25 -> Color.parseColor("#FF0000")
        in 26..50 -> Color.parseColor("#FF8C00")
        in 51..75 -> Color.parseColor("#FFFF00")
        else -> Color.parseColor("#00FF00")
    }