package com.example.definelabtask.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.definelabtask.data.model.ApiDataClass
import com.example.definelabtask.data.room.MatchDao
import com.example.definelabtask.data.room.MatchData
import com.example.definelabtask.data.room.MatchDatabase
import com.example.definelabtask.network.ApiDataInterface
import com.example.definelabtask.network.RetrofitClientInstance
import com.example.definelabtask.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // TAG
    private val TAG = "MainViewModel"

    // api call via retrofit
    private val retrofit = RetrofitClientInstance.getClient()
    private val userApi = retrofit.create(ApiDataInterface::class.java)

    // match dao object
    private lateinit var db: MatchDao

    // live data
    val getAPIDataValue: MutableLiveData<Resource<ApiDataClass>> = MutableLiveData()

    // room
    fun dbSetup(context: Context) {
        db = MatchDatabase.getDatabase(context).matchDao()
    }

    // add data to db
    fun addMatchesToDB(matchData: MatchData) = viewModelScope.launch {
        if (db != null) {
            db.addMatch(matchData)
        }
    }

    // get match data from db
    fun getMatchDataFromDB() = db.getMatchesFromDB().asLiveData()

    // delete match data from db
    fun deleteMatchDataFromDB(matchData: MatchData) = viewModelScope.launch {
        db.deleteMatch(matchData)
    }

    // get data from api
    fun getDataFromAPI() = viewModelScope.launch {
        getAPIDataValue.postValue(Resource.Loading())

        try {

            // sending request
            val response = userApi!!.getDataFromAPI()

            Log.d(TAG, response.isSuccessful.toString())

            if (response.isSuccessful) {
                response.body().let { response ->
                    response.let { data ->
                        Log.d(TAG, data!!.meta.requestId)
                        getAPIDataValue.postValue(Resource.Success(data))
                    }
                }
            } else {
                Log.d(TAG, "Error -> ${response.message()}")
                getAPIDataValue.postValue(Resource.Error(response.message()))
            }

        } catch (e: Exception) {
            Log.d(TAG, "Error -> ${e.message.toString()}")
            getAPIDataValue.postValue(Resource.Error(e.message.toString()))
            e.printStackTrace()
        }
    }
}