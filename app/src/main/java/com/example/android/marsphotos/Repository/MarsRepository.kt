package com.example.android.marsphotos.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.marsphotos.database.MarsDatabase
import com.example.android.marsphotos.database.asDomainModel
import com.example.android.marsphotos.domain.DomainMars
import com.example.android.marsphotos.network.MarsApi
import com.example.android.marsphotos.network.MarsApiFilter
import com.example.android.marsphotos.network.asDataBaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarsRepository(private val database: MarsDatabase) {

    val marsList: LiveData<List<DomainMars>> = Transformations.map(database.marsDao.getMarsProperties()){
        it.asDomainModel()
    }




    suspend fun refreshMarsProperty(filter: MarsApiFilter){
        withContext(Dispatchers.IO){
            val marsPropertyList = MarsApi.retrofitService.getProperty(filter.value)
            database.marsDao.insertAll(marsPropertyList.asDataBaseModel())
        }
    }
}