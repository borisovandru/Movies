package com.borisov.movies.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Borisov Andrey on 28.06.2022
 **/
fun toDateString(value: String): String? =
    SimpleDateFormat(DATE_FORMAT_IN, Locale.getDefault())
        .parse(value)?.let { date ->
            SimpleDateFormat(DATE_FORMAT_OUT, Locale.getDefault()).format(date)
        }

const val DATE_FORMAT_IN = "yyyy-MM-dd"
const val DATE_FORMAT_OUT = "dd MMMM yyyy"