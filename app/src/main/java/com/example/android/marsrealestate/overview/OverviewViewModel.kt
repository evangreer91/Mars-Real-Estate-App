/*
 * Copyright 2018, The Android Open Source Project
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
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// we want to add some basic error handling into the app so the user has a better idea of what happened
// if the internet is not available, we will show our connection error icon and will show the loading animation
// while fetching the Mars property list

// we need to create a liveData in our view model to represent the status of our web request

enum class MarsApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    //we change the status to type MarsApiStatus
    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status

    // update to store a list of Mars properties
    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    // add a live data for triggering navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        // show all properties when the app first loads
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */

    // add MarsApiFilter parameter to getMarsRealEstateProperties
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                _properties.value = MarsApi.retrofitService.getProperties(filter.value)
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    // function sets the navigateToSelectedProperty to the selected property
    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    // function sets the navigateToSelectedProperty to null when navigation is complete
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    // updateFilter takes a filter and gets the filtered properties
    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }
}
