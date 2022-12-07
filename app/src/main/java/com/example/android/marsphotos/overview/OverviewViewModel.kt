/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.marsphotos.overview

import android.app.Application
import androidx.lifecycle.*
import com.example.android.marsphotos.Repository.MarsRepository
import com.example.android.marsphotos.database.getDatabase
import com.example.android.marsphotos.domain.DomainMars
import com.example.android.marsphotos.network.MarsApiFilter
import kotlinx.coroutines.launch


/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
enum class MarsApiStatus { LOADING, ERROR, DONE }
class OverviewViewModel(application: Application) : AndroidViewModel(application) {


    private val _navigateToSelectedProperty = MutableLiveData<DomainMars?>()

    val navigateToSelectedProperty: LiveData<DomainMars?>
        get() = _navigateToSelectedProperty


    private val database = getDatabase(application)
    private val marsRepository = MarsRepository(database)

    init {
        refreshMarsList(MarsApiFilter.SHOW_ALL)
    }

    private fun refreshMarsList(filter: MarsApiFilter){
        viewModelScope.launch {
            marsRepository.refreshMarsProperty(filter)
        }
    }
    fun updateFilter(filter: MarsApiFilter) {
        refreshMarsList(filter)
    }

    val marsList = marsRepository.marsList

    fun displayPropertyDetails(marsProperty: DomainMars) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}
class Factory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OverviewViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}