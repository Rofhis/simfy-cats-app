package com.rofhiwa.simfycatsapp.data.db.dao

import androidx.room.*
import com.rofhiwa.simfycatsapp.data.db.entity.CATS_TABLE_NAME
import com.rofhiwa.simfycatsapp.data.db.entity.CatsEntity
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CatsDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertOne(catsEntity: CatsEntity): Single<Long>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertMany(vararg catsEntity: CatsEntity): Single<MutableList<Long>>

  @Query("SELECT * FROM $CATS_TABLE_NAME WHERE id = :id")
  fun findById(id: Int): Maybe<CatsEntity>

  @Query("SELECT * FROM $CATS_TABLE_NAME LIMIT :from, :limit")
  fun findLazyLoading(from: Int, limit: Int): Maybe<MutableList<CatsEntity>>

  @Query("SELECT * FROM $CATS_TABLE_NAME")
  fun findByAll(): Maybe<MutableList<CatsEntity>>

}