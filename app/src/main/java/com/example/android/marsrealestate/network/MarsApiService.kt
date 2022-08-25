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

package com.example.android.marsrealestate.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

// root address of the Mars server endpoint
private const val BASE_URL = "https://mars.udacity.com/"

// create a RetroFit builder
// pass in a scalars converter that supports returning strings and other primitive types
// specify route web address of our server's endpoint
// call build to create the RetroFit object
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

// public interface that exposes the getProperties method
// defines an interface that explains how retrofit talks to our web server using HTTP requests
interface MarsApiService {
    // getProperties gets the JSON response
    // use the GET annotation and specify the endpoint
    // returns a Retrofit callback that delivers a JSON string response
    @GET("realestate")
    fun getProperties(): Call<String>
}

// public api object that exposes the lazy-initialized Retrofit service
// to create a retrofit service, call retrofit.create passing in the service interface API
// calling MarsApi.retrofitService will return a retrofit object implementing MarsApiService
object MarsApi {
    val retrofitService: MarsApiService by lazy { retrofit.create(MarsApiService::class.java) }
}