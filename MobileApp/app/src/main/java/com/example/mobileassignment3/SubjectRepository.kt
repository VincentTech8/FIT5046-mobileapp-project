package com.example.mobileassignment3

import android.app.Application
import kotlinx.coroutines.flow.Flow

// Repository class responsible for interacting with the SubjectDAO and providing data to the ViewModel
class SubjectRepository (application: Application) {
    // Instance of SubjectDAO to perform database operations
    private var subjectDao: SubjectDAO =
        SubjectDatabase.getDatabase(application).subjectDAO()

    // Flow representing a list of all subjects stored in the database
    val allSubjects: Flow<List<Subject>> = subjectDao.getAllSubjects()

    // Function to insert a new subject into the database
    suspend fun insert(subject: Subject) {
        subjectDao.insertSubject(subject)
    }

    // Function to delete a subject from the database
    suspend fun delete(subject: Subject) {
        subjectDao.deleteSubject(subject)
    }

    // Function to update an existing subject in the database
    suspend fun update(subject: Subject) {
        subjectDao.updateSubject(subject)
    }
}
