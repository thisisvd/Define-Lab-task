package com.example.definelabtask.constants

import com.example.definelabtask.data.room.MatchData

object Constants {

    var isBackButtonVisible = false

    // network call const
    const val API_BASE_URL = "https://api.foursquare.com/v2/venues/"
    const val API_LAT_LANG = "40.7484,-73.9857"
    const val API_TOKEN = "NPKYZ3WZ1VYMNAZ2FLX1WLECAWSMUVOQZOIDBN53F3LVZBPQ"
    const val API_VERSION = "20180616"

    // database const
    const val DATABASE_TABLE = "match_table"
    const val DATABASE_NAME = "database_name"

    // const list
    val matchesMetaData = arrayListOf<MatchData>()
}