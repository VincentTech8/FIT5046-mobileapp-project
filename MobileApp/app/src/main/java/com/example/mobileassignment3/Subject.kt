package com.example.mobileassignment3

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
// Data class representing a Recipe Note entity with properties for database storage
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val name: String,
    val content : String
)
