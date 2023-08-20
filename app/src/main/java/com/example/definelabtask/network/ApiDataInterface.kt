package com.example.definelabtask.network

import com.example.definelabtask.constants.Constants.API_LAT_LANG
import com.example.definelabtask.constants.Constants.API_TOKEN
import com.example.definelabtask.constants.Constants.API_VERSION
import com.example.definelabtask.data.model.ApiDataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiDataInterface {

    @GET("search")
    suspend fun getDataFromAPI(
        @Query("ll")
        ll: String = API_LAT_LANG,
        @Query("oauth_token")
        oauthToken: String = API_TOKEN,
        @Query("v")
        v: String = API_VERSION
    ): Response<ApiDataClass>
}