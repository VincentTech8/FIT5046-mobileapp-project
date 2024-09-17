package com.example.mobileassignment3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubjectViewModel(application: Application) : AndroidViewModel(application) {
    // Instance of SubjectRepository for accessing data
    private val cRepository: SubjectRepository

    // Initialization block to create an instance of SubjectRepository
    init {
        cRepository = SubjectRepository(application)
    }

    // LiveData representing a list of all subjects, converted from Flow using asLiveData()
    val allSubjects: LiveData<List<Subject>> = cRepository.allSubjects.asLiveData()

    // Function to insert a subject into the repository using a coroutine on the IO dispatcher
    fun insertSubject(subject: Subject) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.insert(subject)
    }

    // Function to update a subject in the repository using a coroutine on the IO dispatcher
    fun updateSubject(subject: Subject) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.update(subject)
    }

    // Function to delete a subject from the repository using a coroutine on the IO dispatcher
    fun deleteSubject(subject: Subject) = viewModelScope.launch(Dispatchers.IO) {
        cRepository.delete(subject)
    }
}
