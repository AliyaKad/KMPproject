package org.itis.project.sharedui.features.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.itis.project.sharedui.components.AuthGlassCard
import org.itis.project.sharedui.generated.resources.Res
import org.itis.project.sharedui.generated.resources.error_confirm_password_empty
import org.itis.project.sharedui.generated.resources.error_email_empty
import org.itis.project.sharedui.generated.resources.error_email_invalid
import org.itis.project.sharedui.generated.resources.error_password_empty
import org.itis.project.sharedui.generated.resources.error_password_min_length
import org.itis.project.sharedui.generated.resources.error_password_mismatch
import org.itis.project.sharedui.generated.resources.error_password_no_digit
import org.itis.project.sharedui.generated.resources.error_password_no_letter
import org.itis.project.sharedui.generated.resources.error_username_empty
import org.itis.project.sharedui.generated.resources.error_username_invalid
import org.itis.project.sharedui.generated.resources.error_username_max_length
import org.itis.project.sharedui.generated.resources.error_username_min_length
import org.itis.project.sharedui.generated.resources.login_email_label
import org.itis.project.sharedui.generated.resources.login_password_label
import org.itis.project.sharedui.generated.resources.register_back_button
import org.itis.project.sharedui.generated.resources.register_button
import org.itis.project.sharedui.generated.resources.register_confirm_password_label
import org.itis.project.sharedui.generated.resources.register_title
import org.itis.project.sharedui.generated.resources.register_username_label
import org.itis.project.sharedui.theme.Dimens
import org.itis.project.sharedui.utils.isValidEmail
import org.itis.project.sharedui.utils.isValidUsername
import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthEvent
import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthUiState
import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val authState by viewModel.state.collectAsState()

    LaunchedEffect(authState) {
        if (authState is org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthState.Authorized) {
            onRegisterSuccess()
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            viewModel.handleEvent(AuthEvent.ClearError)
        }
    }

    RegisterScreenContent(
        onRegisterClick = { email, username, password ->
            viewModel.handleEvent(AuthEvent.OnRegister(email, username, password))
        },
        onBackToLogin = onNavigateToLogin,
        errorMessage = (uiState as? AuthUiState.Error)?.message,
        isSubmitting = uiState is AuthUiState.Loading
    )
}

@Composable
private fun RegisterScreenContent(
    onRegisterClick: (String, String, String) -> Unit,
    onBackToLogin: () -> Unit,
    errorMessage: String? = null,
    isSubmitting: Boolean = false
) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var submitAttempted by remember { mutableStateOf(false) }

    val hasMinLength = password.length >= 6
    val hasDigit = password.any { it.isDigit() }
    val hasLetter = password.any { it.isLetter() }

    val errEmailEmpty = stringResource(Res.string.error_email_empty)
    val errEmailInvalid = stringResource(Res.string.error_email_invalid)
    val errUsernameEmpty = stringResource(Res.string.error_username_empty)
    val errUsernameMin = stringResource(Res.string.error_username_min_length)
    val errUsernameMax = stringResource(Res.string.error_username_max_length)
    val errUsernameInvalid = stringResource(Res.string.error_username_invalid)
    val errPasswordEmpty = stringResource(Res.string.error_password_empty)
    val errPasswordMin = stringResource(Res.string.error_password_min_length)
    val errPasswordNoDigit = stringResource(Res.string.error_password_no_digit)
    val errPasswordNoLetter = stringResource(Res.string.error_password_no_letter)
    val errConfirmEmpty = stringResource(Res.string.error_confirm_password_empty)
    val errMismatch = stringResource(Res.string.error_password_mismatch)

    LaunchedEffect(email, username, password, confirmPassword) {
        emailError = when {
            email.isBlank() -> errEmailEmpty
            !isValidEmail(email) -> errEmailInvalid
            else -> null
        }
        usernameError = when {
            username.isBlank() -> errUsernameEmpty
            username.length < 3 -> errUsernameMin
            username.length > 20 -> errUsernameMax
            !isValidUsername(username) -> errUsernameInvalid
            else -> null
        }
        passwordError = when {
            password.isBlank() -> errPasswordEmpty
            password.length < 6 -> errPasswordMin
            !hasDigit -> errPasswordNoDigit
            !hasLetter -> errPasswordNoLetter
            else -> null
        }
        confirmPasswordError = when {
            confirmPassword.isBlank() -> errConfirmEmpty
            password != confirmPassword -> errMismatch
            else -> null
        }
    }

    val isFormValid = emailError == null && usernameError == null &&
            passwordError == null && confirmPasswordError == null &&
            email.isNotBlank() && username.isNotBlank() &&
            password.isNotBlank() && !isSubmitting

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.spacing24, vertical = Dimens.spacing24)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(80.dp).clip(CircleShape),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.Stars,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(Modifier.height(Dimens.spacing12))

            Text(
                text = stringResource(Res.string.register_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Создайте аккаунт SpaceVue",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = Dimens.spacing4)
            )

            Spacer(Modifier.height(Dimens.spacing24))

            AuthGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 440.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.spacing12)) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(stringResource(Res.string.login_email_label)) },
                        leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        isError = submitAttempted && emailError != null,
                        supportingText = {
                            if (submitAttempted && emailError != null) {
                                Text(emailError!!, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text(stringResource(Res.string.register_username_label)) },
                        leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        isError = submitAttempted && usernameError != null,
                        supportingText = {
                            if (submitAttempted && usernameError != null) {
                                Text(usernameError!!, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(Res.string.login_password_label)) },
                        leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        isError = submitAttempted && passwordError != null,
                        supportingText = {
                            if (submitAttempted && passwordError != null) {
                                Text(passwordError!!, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        singleLine = true
                    )

                    AnimatedVisibility(password.isNotBlank()) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            PasswordRequirement(
                                met = hasMinLength,
                                text = "Минимум 6 символов"
                            )
                            PasswordRequirement(
                                met = hasDigit,
                                text = "Хотя бы одна цифра"
                            )
                            PasswordRequirement(
                                met = hasLetter,
                                text = "Хотя бы одна буква"
                            )
                        }
                    }

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text(stringResource(Res.string.register_confirm_password_label)) },
                        leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        isError = submitAttempted && confirmPasswordError != null,
                        supportingText = {
                            if (submitAttempted && confirmPasswordError != null) {
                                Text(confirmPasswordError!!, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        singleLine = true
                    )

                    AnimatedVisibility(
                        visible = errorMessage != null,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        if (errorMessage != null) {
                            Surface(
                                color = MaterialTheme.colorScheme.errorContainer,
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = errorMessage,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    modifier = Modifier.padding(Dimens.spacing12)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(Dimens.spacing4))

                    Button(
                        onClick = {
                            submitAttempted = true
                            if (isFormValid) onRegisterClick(email.trim(), username.trim(), password)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.buttonHeight),
                        enabled = isFormValid
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(stringResource(Res.string.register_button))
                        }
                    }

                    TextButton(
                        onClick = onBackToLogin,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(Res.string.register_back_button))
                    }
                }
            }
        }
    }
}

@Composable
private fun PasswordRequirement(met: Boolean, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = if (met) Icons.Filled.CheckCircle else Icons.Filled.Close,
            contentDescription = null,
            tint = if (met) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error.copy(alpha = 0.6f),
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = if (met) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
    }
}