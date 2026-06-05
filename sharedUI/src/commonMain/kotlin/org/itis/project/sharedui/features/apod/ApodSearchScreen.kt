package org.itis.project.sharedui.features.apod

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodSearch.ApodSearchEvent
import org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodSearch.ApodSearchViewModel
import org.itis.project.sharedui.components.AppButton
import org.itis.project.sharedui.components.AppCard
import org.itis.project.sharedui.components.ButtonVariant
import org.itis.project.sharedui.components.GradientBackground
import org.itis.project.sharedui.theme.Dimens
import org.itis.project.sharedui.theme.SpaceTheme
import org.koin.compose.koinInject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun ApodSearchScreen(
    onBack: () -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
//    val vm = retain { ApodSearchViewModel() }
//    val state by vm.state.collectAsStateWithLifecycle()

    val vm: ApodSearchViewModel = koinInject()
    val state by vm.state.collectAsStateWithLifecycle()

    val currentDate = Clock.System.now()
        .toLocalDateTime(TimeZone.UTC)
        .date

    var tempSelectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(showDatePicker) {
        if (showDatePicker) {
            tempSelectedDate = state.selectedDate ?: currentDate
        }
    }


    SpaceTheme {
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(Dimens.spacing16)
            ) {
                // Заголовок
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                    Text(
                        text = "Поиск картинки по дате",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.spacing16))

                // Карточка выбора даты
                AppCard(glassEffect = true) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.spacing16),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacing12)
                    ) {
                        Text(
                            text = "Выберите дату",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        OutlinedTextField(
                            value = state.selectedDate?.let { formatDate(it) } ?: "Не выбрана",
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = { showDatePicker = true }) {
                                    Icon(Icons.Default.CalendarToday, contentDescription = "Выбрать дату")
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.spacing16))

                when {
                    state.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(300.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    state.error != null -> {
                        AppCard(glassEffect = true) {
                            Column(
                                modifier = Modifier.padding(Dimens.spacing16),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = state.error!!,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(Dimens.spacing12))
                                AppButton(
                                    onClick = { vm.obtainIntent(ApodSearchEvent.LoadApod) },
                                    text = "Повторить",
                                    variant = ButtonVariant.Secondary,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                    state.apod != null -> {
                        AppCard(glassEffect = true) {
                            Column {
                                AsyncImage(
                                    model = state.apod!!.url,
                                    contentDescription = state.apod!!.title,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                )
                                Column(
                                    modifier = Modifier.padding(Dimens.spacing16),
                                    verticalArrangement = Arrangement.spacedBy(Dimens.spacing8)
                                ) {
                                    Text(
                                        text = state.apod!!.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = state.apod!!.date,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = state.apod!!.explanation.take(120) + "...",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.height(Dimens.spacing8))
                                    AppButton(
                                        onClick = {  state.apod?.let { apod ->
                                            onNavigateToDetail(apod.date)
                                        } },
                                        text = "Открыть полностью",
                                        variant = ButtonVariant.Primary,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                    else -> {
                        AppCard(glassEffect = true) {
                            Text(
                                text = "Выберите дату, чтобы увидеть картинку дня",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(Dimens.spacing16)
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            onDateSelected = { date ->
                vm.obtainIntent(ApodSearchEvent.SelectDate(date))
                showDatePicker = false
            },
            initialDate = tempSelectedDate ?: currentDate,
            currentDate = currentDate
        )
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    initialDate: LocalDate,
    currentDate: LocalDate
) {
    val initialMillis = initialDate.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
    val currentMillis = currentDate.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialMillis,
        yearRange = 1995..currentDate.year
    )

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Выберите дату") },
        text = {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                title = null
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val instant = Instant.fromEpochMilliseconds(millis)
                        val localDate = instant.toLocalDateTime(TimeZone.UTC).date
                        if (localDate <= currentDate) {
                            onDateSelected(localDate)
                        }
                        onDismissRequest()
                    } ?: onDismissRequest()
                }
            ) {
                Text("Выбрать")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Отмена")
            }
        }
    )
}

private fun formatDate(date: LocalDate): String {
    return "${date.dayOfMonth}.${date.monthNumber}.${date.year}"
}