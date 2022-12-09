package com.example.android.marsphotos.worker

import android.content.Context
import androidx.work.CoroutineWorker

import androidx.work.WorkerParameters
import com.example.android.marsphotos.Repository.MarsRepository
import com.example.android.marsphotos.database.getDatabase
import com.example.android.marsphotos.network.MarsApiFilter
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {


    companion object{
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = MarsRepository(database)
        return try {
            repository.refreshMarsProperty(MarsApiFilter.SHOW_ALL)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }


}