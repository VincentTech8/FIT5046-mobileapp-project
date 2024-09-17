package com.example.mobileassignment3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

// Composable function to display the recipe notes screen
@Composable
fun RecipeNotes(navController: NavHostController, subjectViewModel: SubjectViewModel) {
    // Observing the list of subjects from the view model
    val subjects by subjectViewModel.allSubjects.observeAsState(emptyList())
    // State for the selected subject
    val selectedSubject = remember { mutableStateOf<Subject?>(null) }
    // State for the insert dialog
    val insertDialog = remember { mutableStateOf(false) }

    // Column layout to arrange UI components vertically
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // LazyColumn to efficiently display a list of items
        LazyColumn {
            itemsIndexed(subjects) { index, subject ->
                // Displaying each subject item
                SubjectItem(
                    subject = subject,
                    onEdit = { selectedSubject.value = subject },
                    onDelete = { subjectViewModel.deleteSubject(subject) }
                )
                Divider(color = Color.Gray, thickness = 5.dp)
            }
        }
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            // Button to add a new recipe
            Button(
                onClick = { insertDialog.value = true },
                colors = ButtonDefaults.buttonColors(Color(65, 105, 225))) {
                Text("Add Recipe")
            }
        }
    }

    // Displaying insert subject dialog if insertDialog is true
    if (insertDialog.value) {
        InsertSubjectDialog(
            onDismiss = { insertDialog.value = false },
            onSave = { subjectName, subjectContent ->
                subjectViewModel.insertSubject(
                    Subject(
                        name = subjectName,
                        content = subjectContent
                    )
                )
            }
        )
    }

    selectedSubject.value?.let { subject ->
        EditSubjectDialog(
            subject = subject,
            onDismiss = { selectedSubject.value = null },
            onSave = { updatedSubject ->
                subjectViewModel.updateSubject(updatedSubject)
                selectedSubject.value = null
            }
        )
    }
}

// Composable function to display the insert subject dialog
@Composable
fun InsertSubjectDialog(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    // State for the subject name and content
    var subjectName by remember { mutableStateOf("") }
    var subjectContent by remember { mutableStateOf("") }

    // Alert dialog to prompt the user for subject details
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Recipe") },
        confirmButton = {
            Button(
                onClick = {
                    onSave(subjectName, subjectContent)
                    onDismiss()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            Column {
                // Text field for entering recipe name
                TextField(
                    value = subjectName,
                    onValueChange = { subjectName = it },
                    label = { Text("Recipe Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                // Text field for entering recipe content
                TextField(
                    value = subjectContent,
                    onValueChange = { subjectContent = it },
                    label = { Text("Recipe Content") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@Composable
fun EditSubjectDialog(subject: Subject, onDismiss: () -> Unit, onSave: (Subject) -> Unit) {
    var editedSubject by remember { mutableStateOf(subject) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Recipe") },
        confirmButton = {
            Button(
                onClick = {
                    onSave(editedSubject)
                    onDismiss()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            Column {
                // TextField for editing subject name
                TextField(
                    value = editedSubject.name.toString(),
                    onValueChange = { editedSubject = editedSubject.copy(name = it) },
                    label = { Text("Recipe Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                // TextField for editing subject content
                TextField(
                    value = editedSubject.content.toString(),
                    onValueChange = { editedSubject = editedSubject.copy(content = it) },
                    label = { Text("Recipe Content") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
