package com.rofhiwa.simfycatsapp.data.db.repository

import com.rofhiwa.simfycatsapp.data.db.AppDatabase
import com.rofhiwa.simfycatsapp.data.db.dao.CatsDao
import com.rofhiwa.simfycatsapp.data.db.entity.CatsEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CatsRepository @Inject constructor() {

  @Inject
  lateinit var appDatabase: AppDatabase

  private val provideCatsDao: CatsDao by lazy {
    appDatabase.provideCatsDao()
  }

  fun insertMany(
    catsEntityList: MutableList<CatsEntity>,
    onSuccess: (Boolean) -> Unit,
    onError: (Throwable) -> Unit
  ): Disposable {

    val cats = catsEntityList.toTypedArray()

    return provideCatsDao.insertMany(*cats)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ result -> onSuccess(result.isNotEmpty()) }, { t -> onError(t) })
  }

  fun findByAll(
    onSuccess: (MutableList<CatsEntity>) -> Unit,
    onError: (Throwable) -> Unit
  ): Disposable {
    return provideCatsDao.findByAll()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ result -> onSuccess(result) }, { t -> onError(t) })
  }
}