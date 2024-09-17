package com.example.mobileassignment3

// Import necessary packages
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mobileassignment3.ui.theme.MobileAssignment3Theme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale

// MainActivity class responsible for handling app lifecycle and UI
class MainActivity : ComponentActivity() {

    // Firebase Authentication instance
    private lateinit var auth: FirebaseAuth

    // ViewModel instances
    private val viewModel: FoodViewModel by viewModels()
    private val viewModel1: SubjectViewModel by viewModels()

    // onCreate function called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth

        // Set content to LoginScreen composable function
        setContent {
            LoginScreen()
        }
    }

    // onStart function called when the activity is about to become visible
    @RequiresApi(64)
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // If user is signed in, set content to RegistrationScreen composable function
            setContent {
                RegistrationScreen()
            }
        }
    }

    // Function to create a launcher for Google Authentication
    @Composable
    fun authLuncher(
        onAuthComplete: (AuthResult) -> Unit,
        onAuthError: (ApiException) -> Unit
    ): ManagedActivityResultLauncher<Intent, ActivityResult> {
        val scope = rememberCoroutineScope()
        return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                scope.launch {
                    val authResult =
                        com.google.firebase.ktx.Firebase.auth.signInWithCredential(credential)
                            .await()
                    onAuthComplete(authResult)
                }
            } catch (e: ApiException) {
                onAuthError(e)
            }
        }
    }

    // Function to create a new user account with email and password
    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        // Create user with email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    sendEmailVerification()
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        // [END create_user_with_email]
    }

    // Function to send email verification link to the user
    private fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                // Email Verification sent
                displayErrorMessage("Verification Email sent.")
            }
        // [END send_email_verification]
    }

    // Function to sign in with email and password
    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    displayErrorMessage("Authentication success. Logging you in ...")
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    displayErrorMessage("Authentication failed")
                }
            }
        // [END sign_in_with_email]
    }

    // Function to update UI based on user authentication status
    private fun updateUI(user: FirebaseUser?) {
        setContent {
            MobileAssignment3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation(viewModel = viewModel, viewModel1 = viewModel1,
                        onLogout = {
                            // Perform logout action here
                            auth.signOut()
                            setContent {
                                LoginScreen()
                            }
                        })
                }
            }
        }
    }

    // Function to display error message in an AlertDialog
    private fun displayErrorMessage(errorMessage: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(errorMessage)
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            // Dismiss the dialog if the user clicks the "OK" button
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    companion object {
        private const val TAG = "register"
    }

    // Composable function to display the Login screen UI
    @Composable
    fun LoginScreen() {
        // Define mutable state variables
        var errorText by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var user by remember { mutableStateOf(com.google.firebase.ktx.Firebase.auth.currentUser) }
        var launcher = authLuncher(
            onAuthComplete = { result ->
                user = result.user
            }, onAuthError = {
                user = null
            })
        val token = stringResource(R.string.web_id)
        val context = LocalContext.current

        // Column layout to arrange UI elements vertically
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,

            ) {
            // Text displaying "Login" title
            Text(
                text = "Log in",
                style = MaterialTheme.typography.headlineMedium,
            )

            // OutlinedTextField for entering email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // OutlinedTextField for entering password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    // IconButton to toggle password visibility
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Button for login action
            Button(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = {
                    errorText = ""
                    if (email.isBlank()) {
                        errorText += "Email can't be blank. "
                    } else if (!email.contains('@')) {
                        errorText += "Invalid Email address. "
                    }
                    if (password.isBlank()) {
                        errorText += "Password can't be blank. "
                    }
                    if (email.isNotBlank() && password.isNotBlank() && email.contains('@')) {
                        signIn(email.trim(), password.trim())
                    } else {
                        displayErrorMessage(errorText)
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(65, 105, 225))
            ) {
                Text(
                    text = "Login"
                )
            }

            // Button for Google sign-in
            Button(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape),
                onClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()
                    val gsc = GoogleSignIn.getClient(context, gso)
                    launcher.launch(gsc.signInIntent)
                    if (user != null) {
                        updateUI(user)
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.White),
            ) {
                // Row layout to align image and text horizontally
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Image for Google icon
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    // Text for "Sign in with Google"
                    Text(text = "Sign in with Google", color = Color.Black)
                }
            }

            // Button to navigate to registration screen
            Button(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = {
                    setContent {
                        RegistrationScreen()
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(255,255,255), contentColor = Color(65, 105, 225) )
            ) {
                Text("Register")
            }
        }
    }

    // Composable function to display the registration screen UI
    @RequiresApi(0)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RegistrationScreen() {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmedPassword by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var confirmedPasswordVisible by remember { mutableStateOf(false) }
        var isExpanded by remember { mutableStateOf(false) }
        val genders = listOf(
            "Male", "Female"
        )
        var selectedGender by remember { mutableStateOf(genders[0]) }

        // Date of birth variables
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
        calendar.set(2024, 0, 1) // month (0) is January
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = Instant.now().toEpochMilli()
        )
        var showDatePicker by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }
        var dateChosenFlag by remember { mutableStateOf(false) }
        var userAge by remember { mutableStateOf(0) }


        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            // Text displaying "Registration" title
            Text(
                text = "Registration",
                style = MaterialTheme.typography.headlineMedium,
            )

            // OutlinedTextField for entering email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Dropdown menu for selecting gender
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it },
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .focusProperties {
                            canFocus = false
                        }
                        .padding(bottom = 8.dp),
                    readOnly = true,
                    value = selectedGender,
                    onValueChange = {},
                    label = { Text("Gender") },
                    //manages the arrow icon up and down
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                )
                {
                    genders.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedGender = selectionOption
                                isExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }

            // Text displaying "Date of Birth" title
            Text(
                text = "Date of Birth",
                style = MaterialTheme.typography.labelMedium
            )
            // Display date picker dialog if needed
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = {
                        showDatePicker = false
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showDatePicker = false
                            // selectedDateMillis!! null safety because type declared as Long?
                            selectedDate = datePickerState.selectedDateMillis!!
                            dateChosenFlag = true
                            Log.d(
                                "Date of birth after changed",
                                "${formatter.format(Date(selectedDate))}"
                            )
                        }) {
                            Text(text = "OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDatePicker = false
                        }) {
                            Text(text = "Cancel")
                        }
                    }
                ) // End of dialog
                { // Still within column scope
                    DatePicker(
                        state = datePickerState
                    )
                }
            }
            // Button to show date picker dialog
            Button(
                onClick = {
                    showDatePicker = true
                },
                colors = ButtonDefaults.buttonColors(Color(65, 105, 225))
            ) {
                Text(text = "Choose from calendar")
            }

            // Display chosen date of birth
            if (dateChosenFlag) {
                Text(
                    text = "Chosen Date of Birth: ${formatter.format(Date(selectedDate))}"
                )
            }

            // OutlinedTextField for entering password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    // IconButton to toggle password visibility
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // OutlinedTextField for confirming password
            OutlinedTextField(
                value = confirmedPassword,
                onValueChange = { confirmedPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = if (confirmedPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (confirmedPasswordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Providing localized description for accessibility services
                    val description =
                        if (confirmedPasswordVisible) "Hide password" else "Show password"

                    // IconButton to toggle password visibility
                    IconButton(onClick = { confirmedPasswordVisible = !confirmedPasswordVisible }) {
                        Icon(imageVector = image, description)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            // Button for registration action
            Button(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = {
                    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
                    var errorText = ""
                    val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")

                    if (email.isBlank()) {
                        errorText += "Email can't be blank. "
                    } else if (!email.matches(emailRegex)) {
                        errorText += "Invalid Email address. "
                    }

                    if (password.isBlank() || confirmedPassword.isBlank()) {
                        errorText += "Both password fields can't be blank. "
                    } else if (!password.matches(passwordRegex)) {
                    errorText += "Password must contain at least one uppercase letter, one lowercase letter, one number and one special character."
                }
                    if (!password.contentEquals(confirmedPassword)) {
                        errorText += "Both passwords have to be identical. "
                    } else if (password.length < 8) {
                        // Password has to be more than 8 characters
                        errorText += "Password must contain 8 characters or more. "
                    }

                    if (!dateChosenFlag) {
                        errorText += "Please choose at least one date of birth. "
                    } else if (selectedDate >= System.currentTimeMillis()) {
                        // Since the minimum age requirement is 10
                        errorText += "Date of birth can't be in the future. "

                    } else {
                        // Calculating user age by converting 2 millisecond long to Instant
                        val chosenDob = LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(selectedDate),
                            ZoneOffset.UTC
                        )
                        val currentTime = LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(System.currentTimeMillis()),
                            ZoneOffset.UTC
                        )

                        // Calculate the difference in years
                        userAge = ChronoUnit.YEARS.between(chosenDob, currentTime).toInt()
                        Log.d("time difference", userAge.toString())
                        if (userAge < 10) {
                            errorText += "The minimum age requirement is 10. "
                        }
                    }
                    if (email.isNotBlank() && password.isNotBlank() && email.matches(emailRegex) && confirmedPassword.isNotBlank() && dateChosenFlag && (userAge >= 10) && (password.length >= 8) && password.matches(passwordRegex)) {
                        Log.d("email", email)
                        Log.d("gender", selectedGender)
                        Log.d("password", password)
                        Log.d("confirmed password", confirmedPassword)
                        Log.d("chosen dob", "${formatter.format(Date(selectedDate))}")

                        displayErrorMessage("Logging you in...")
                        createAccount(email.trim(), password.trim())

                        savingRegistrationDataToFirestore(
                            email,
                            selectedGender,
                            "${formatter.format(Date(selectedDate))}"
                        )
                        // If account is created successful, store user registration data into the Cloud Firestore database
                    } else {
                        displayErrorMessage(errorText)
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(65, 105, 225))
            ) {
                Text(
                    text = "Register"
                )
            }
            // Button to navigate to login screen
            Button(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = {
                    setContent {
                        LoginScreen()
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(65, 105, 225))
            ) {
                Text(
                    text = "Log In"
                )
            }
        }

    }
}

// Function to save user registration data to Firestore
fun savingRegistrationDataToFirestore(
    email: String,
    gender: String,
    dateOfBirth: String
) {
    val db = Firebase.firestore
    // Create a new user with a first and last name
    val user = hashMapOf(
        "email" to email,
        "gender" to gender,
        "dateOfBirth" to dateOfBirth,
    )

    // Add a new document with a generated ID
    db.collection("users")
        .add(user)
        .addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }
}
