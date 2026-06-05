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
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var isLoginEnabled by remember { mutableStateOf(false) }

    val errorEmailEmpty = stringResource(Res.string.error_email_empty)
    val errorEmailInvalid = stringResource(Res.string.error_email_invalid)
    val errorPasswordEmpty = stringResource(Res.string.error_password_empty)
    val errorPasswordMinLength = stringResource(Res.string.error_password_min_length)

    LaunchedEffect(email, password) {
        emailError = when {
            email.isBlank() -> errorEmailEmpty
            !isValidEmail(email) -> errorEmailInvalid
            else -> null
        }

        passwordError = when {
            password.isBlank() -> errorPasswordEmpty
            password.length < 6 -> errorPasswordMinLength
            else -> null
        }

        isLoginEnabled = emailError == null && passwordError == null &&
                email.isNotBlank() && password.isNotBlank()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.login_title),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(Res.string.login_email_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(Res.string.login_password_label)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError != null,
            supportingText = { passwordError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onLoginClick(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = isLoginEnabled
        ) {
            Text(stringResource(Res.string.login_button))
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onRegisterClick) {
            Text(stringResource(Res.string.login_register_button))
        }
    }
}