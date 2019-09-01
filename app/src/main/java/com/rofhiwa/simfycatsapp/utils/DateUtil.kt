package com.rofhiwa.simfycatsapp.utils

import java.text.DateFormat
import java.util.*

object DateUtil {

    fun now(): String {
        return DateFormat.getDateTimeInstance().format(Date())
    }
}