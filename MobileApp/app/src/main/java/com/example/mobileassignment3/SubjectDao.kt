package com.example.mobileassignment3

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// Data Access Object (DAO) interface for accessing Subject entities in the database
@Dao
interface SubjectDAO {
    // Query method to retrieve all subjects from the database as a flow of lists of subjects
    @Query("SELECT * FROM Subject")
    fun getAllSubjects(): Flow<List<Subject>>
    // Insert method to add a new subject to the database, suspend function for coroutine compatibility
    @Insert
    suspend fun insertSubject(subject: Subject)
    // Update method to modify an existing subject in the database, suspend function for coroutine compatibility
    @Update
    suspend fun updateSubject(subject: Subject)
    // Delete method to remove a subject from the database, suspend function for coroutine compatibility
    @Delete
    suspend fun deleteSubject(subject: Subject)
}
