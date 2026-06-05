package org.itis.project.sharedui.features.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.itis.project.sharedui.utils.isValidEmail
import org.itis.project.sharedui.utils.isValidUsername
import org.itis.project.sharedui.utils.isValidPassword

@Composable
fun RegisterScreen(
    onRegisterClick: (String, String, String) -> Unit,
    onBackToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    var isRegisterEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(email, username, password, confirmPassword) {
        emailError = when {
            email.isBlank() -> "Email не может быть пустым"
            !isValidEmail(email) -> "Введите корректный email"
            else -> null
        }

        usernameError = when {
            username.isBlank() -> "Имя пользователя не может быть пустым"
            username.length < 3 -> "Имя пользователя должно содержать минимум 3 символа"
            username.length > 20 -> "Имя пользователя не должно превышать 20 символов"
            !isValidUsername(username) -> "Имя пользователя может содержать только буквы, цифры и подчёркивание"
            else -> null
        }

        passwordError = when {
            password.isBlank() -> "Пароль не может быть пустым"
            password.length < 6 -> "Пароль должен содержать минимум 6 символов"
            !isValidPassword(password) -> "Пароль должен содержать хотя бы одну цифру и одну букву"
            else -> null
        }

        confirmPasswordError = when {
            confirmPassword.isBlank() -> "Подтвердите пароль"
            password != confirmPassword -> "Пароли не совпадают"
            else -> null
        }

        isRegisterEnabled = emailError == null &&
                usernameError == null &&
                passwordError == null &&
                confirmPasswordError == null &&
                email.isNotBlank() &&
                username.isNotBlank() &&
                password.isNotBlank() &&
                confirmPassword.isNotBlank()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            isError = usernameError != null,
            supportingText = { usernameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError != null,
            supportingText = { passwordError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            isError = confirmPasswordError != null,
            supportingText = { confirmPasswordError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (isRegisterEnabled) {
                    onRegisterClick(email, username, password)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isRegisterEnabled
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onBackToLogin) {
            Text("Back to Login")
        }
    }
}