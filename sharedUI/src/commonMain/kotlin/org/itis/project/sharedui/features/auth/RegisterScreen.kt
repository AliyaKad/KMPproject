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
import org.itis.project.sharedui.generated.resources.Res
import org.itis.project.sharedui.generated.resources.*
import org.itis.project.sharedui.utils.isValidEmail
import org.itis.project.sharedui.utils.isValidUsername
import org.itis.project.sharedui.utils.isValidPassword
import org.jetbrains.compose.resources.stringResource

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

    val errorEmailEmpty = stringResource(Res.string.error_email_empty)
    val errorEmailInvalid = stringResource(Res.string.error_email_invalid)
    val errorUsernameEmpty = stringResource(Res.string.error_username_empty)
    val errorUsernameMinLength = stringResource(Res.string.error_username_min_length)
    val errorUsernameMaxLength = stringResource(Res.string.error_username_max_length)
    val errorUsernameInvalid = stringResource(Res.string.error_username_invalid)
    val errorPasswordEmpty = stringResource(Res.string.error_password_empty)
    val errorPasswordMinLength = stringResource(Res.string.error_password_min_length)
    val errorPasswordNoDigit = stringResource(Res.string.error_password_no_digit)
    val errorPasswordNoLetter = stringResource(Res.string.error_password_no_letter)
    val errorConfirmPasswordEmpty = stringResource(Res.string.error_confirm_password_empty)
    val errorPasswordMismatch = stringResource(Res.string.error_password_mismatch)

    val loginTitle = stringResource(Res.string.login_title)
    val emailLabel = stringResource(Res.string.login_email_label)
    val passwordLabel = stringResource(Res.string.login_password_label)
    val registerTitle = stringResource(Res.string.register_title)
    val usernameLabel = stringResource(Res.string.register_username_label)
    val confirmPasswordLabel = stringResource(Res.string.register_confirm_password_label)
    val registerButton = stringResource(Res.string.register_button)
    val backToLogin = stringResource(Res.string.register_back_button)

    LaunchedEffect(email, username, password, confirmPassword) {
        emailError = when {
            email.isBlank() -> errorEmailEmpty
            !isValidEmail(email) -> errorEmailInvalid
            else -> null
        }

        usernameError = when {
            username.isBlank() -> errorUsernameEmpty
            username.length < 3 -> errorUsernameMinLength
            username.length > 20 -> errorUsernameMaxLength
            !isValidUsername(username) -> errorUsernameInvalid
            else -> null
        }

        passwordError = when {
            password.isBlank() -> errorPasswordEmpty
            password.length < 6 -> errorPasswordMinLength
            !isValidPassword(password) -> {
                if (!password.any { it.isDigit() } && !password.any { it.isLetter() }) {
                    "$errorPasswordNoDigit & $errorPasswordNoLetter"
                } else if (!password.any { it.isDigit() }) {
                    errorPasswordNoDigit
                } else {
                    errorPasswordNoLetter
                }
            }
            else -> null
        }

        confirmPasswordError = when {
            confirmPassword.isBlank() -> errorConfirmPasswordEmpty
            password != confirmPassword -> errorPasswordMismatch
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
            text = registerTitle,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(emailLabel) },
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
            label = { Text(usernameLabel) },
            modifier = Modifier.fillMaxWidth(),
            isError = usernameError != null,
            supportingText = { usernameError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(passwordLabel) },
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
            label = { Text(confirmPasswordLabel) },
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
            Text(registerButton)
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onBackToLogin) {
            Text(backToLogin)
        }
    }
}