package com.example.mobileassignment3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subject::class], version = 2, exportSchema = false)
// Abstract class representing the Room database instance for Subject entities
abstract class SubjectDatabase : RoomDatabase() {
    // Abstract method to retrieve the SubjectDAO interface for accessing Subject entities
    abstract fun subjectDAO(): SubjectDAO

    // Companion object for database initialization and singleton pattern
    companion object {
        // Volatile variable to ensure visibility of INSTANCE to all threads
        @Volatile
        private var INSTANCE: SubjectDatabase? = null

        // Function to get or create the database instance
        fun getDatabase(context: Context): SubjectDatabase {
            // Return existing instance if available, otherwise create a new one
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SubjectDatabase::class.java,
                    "subject_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
