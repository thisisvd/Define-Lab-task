package com.example.definelabtask.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.definelabtask.constants.Constants.DATABASE_NAME

@Database(
    entities = [MatchData::class],
    version = 1,
    exportSchema = false
)
abstract class MatchDatabase : RoomDatabase() {

    abstract fun matchDao(): MatchDao

    companion object {
        fun getDatabase(context: Context): MatchDatabase {

            return Room.databaseBuilder(
                context,
                MatchDatabase::class.java, DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}