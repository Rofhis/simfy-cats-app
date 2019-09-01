package com.rofhiwa.simfycatsapp.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.rofhiwa.simfycatsapp.data.db.AppDatabase
import com.rofhiwa.simfycatsapp.data.db.dao.CatsDao
import com.rofhiwa.simfycatsapp.data.db.entity.CatsEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatsDaoTest {

  private lateinit var appDatabase: AppDatabase
  private lateinit var catsDao: CatsDao

  @Before
  fun initDb() {
    appDatabase =
      Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, AppDatabase::class.java)
        .allowMainThreadQueries()
        .build()

    catsDao = appDatabase.provideCatsDao()
  }

  @After
  fun closeDb() {
    appDatabase.close()
  }

  @Test fun insertManyCatsDataReturnsNumberOfInsertedRows() {
    catsDao.insertMany(*getMockEntityData().toTypedArray()).test().assertValue {
      it.isNotEmpty()
    }
  }

  @Test fun findAllCatsReturnsCatsEntityList() {
    catsDao.insertMany(*getMockEntityData().toTypedArray()).blockingGet()
    catsDao.findByAll().test().assertValue {
      it.isNotEmpty()
    }
  }

  private fun getMockEntityData(): List<CatsEntity> {
    val catsEntity = CatsEntity(
      title = "Image 1",
      imageUrl = "Image url",
      description = "Image 1 test image"
    )
    return listOf(catsEntity, catsEntity, catsEntity, catsEntity)
  }
}