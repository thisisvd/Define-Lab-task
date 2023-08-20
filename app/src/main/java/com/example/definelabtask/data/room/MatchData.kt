package com.example.definelabtask.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.definelabtask.constants.Constants.DATABASE_TABLE

@Entity(DATABASE_TABLE)
data class MatchData(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
)