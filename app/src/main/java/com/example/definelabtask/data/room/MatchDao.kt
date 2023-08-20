package com.example.definelabtask.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMatch(matchData: MatchData)

    @Delete
    suspend fun deleteMatch(matchData: MatchData)

    @Query("SELECT * FROM match_table")
    fun getMatchesFromDB(): Flow<List<MatchData>>

    @Query("SELECT * FROM match_table WHERE id =:id")
    fun searchMatchByID(id: String): Flow<List<MatchData>>
}