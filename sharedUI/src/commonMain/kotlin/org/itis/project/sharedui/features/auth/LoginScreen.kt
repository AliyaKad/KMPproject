package org.itis.project.sharedui.features.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.RocketLaunch
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
import org.itis.project.sharedui.generated.resources.error_email_empty
import org.itis.project.sharedui.generated.resources.error_email_invalid
import org.itis.project.sharedui.generated.resources.error_password_empty
import org.itis.project.sharedui.generated.resources.error_password_min_length
import org.itis.project.sharedui.generated.resources.login_button
import org.itis.project.sharedui.generated.resources.login_email_label
import org.itis.project.sharedui.generated.resources.login_password_label
import org.itis.project.sharedui.generated.resources.login_register_button
import org.itis.project.sharedui.generated.resources.login_title
import org.itis.project.sharedui.theme.Dimens
import org.itis.project.sharedui.utils.isValidEmail
import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthEvent
import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthUiState
import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val authState by viewModel.state.collectAsState()

    LaunchedEffect(authState) {
        if (authState is org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthState.Authorized) {
            onLoginSuccess()
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            viewModel.handleEvent(AuthEvent.ClearError)
        }
    }

    LoginScreenContent(
        onLoginClick = { email, password ->
            viewModel.handleEvent(AuthEvent.OnLogin(email, password))
        },
        onRegisterClick = onNavigateToRegister,
        errorMessage = (uiState as? AuthUiState.Error)?.message,
        isSubmitting = uiState is AuthUiState.Loading
    )
}

@Composable
private fun LoginScreenContent(
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    errorMessage: String? = null,
    isSubmitting: Boolean = false
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var submitAttempted by remember { mutableStateOf(false) }

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
    }

    val isLoginEnabled = emailError == null && passwordError == null &&
            email.isNotBlank() && password.isNotBlank() && !isSubmitting

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
                .padding(horizontal = Dimens.spacing24, vertical = Dimens.spacing32)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(96.dp).clip(CircleShape),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.RocketLaunch,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(Modifier.height(Dimens.spacing16))

            Text(
                text = stringResource(Res.string.login_title),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Войдите, чтобы продолжить",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = Dimens.spacing4)
            )

            Spacer(Modifier.height(Dimens.spacing32))

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
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(Res.string.login_password_label)) },
                        leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Скрыть пароль" else "Показать пароль"
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
                            if (isLoginEnabled) onLoginClick(email.trim(), password)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.buttonHeight),
                        enabled = isLoginEnabled
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(stringResource(Res.string.login_button))
                        }
                    }

                    TextButton(
                        onClick = onRegisterClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(Res.string.login_register_button))
                    }
                }
            }
        }
    }
}