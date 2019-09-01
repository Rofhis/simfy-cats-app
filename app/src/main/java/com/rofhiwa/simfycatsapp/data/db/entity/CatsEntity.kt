package com.rofhiwa.simfycatsapp.data.db.entity

import android.text.format.DateUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rofhiwa.simfycatsapp.utils.DateUtil
import java.util.*

const val CATS_TABLE_NAME = "cats"

@Entity(tableName = CATS_TABLE_NAME)
data class CatsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "image_url")
    var imageUrl: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "created_at")
    var createAt: String = DateUtil.now()
)