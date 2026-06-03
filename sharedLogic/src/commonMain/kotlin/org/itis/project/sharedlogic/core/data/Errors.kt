package org.itis.project.sharedlogic.core.data

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode

enum class ErrorContext { Nasa, Iss }

object Errors {
    fun friendly(throwable: Throwable, context: ErrorContext): String = when (throwable) {
        is ClientRequestException -> when (throwable.response.status) {
            HttpStatusCode.TooManyRequests, HttpStatusCode.Forbidden -> when (context) {
                ErrorContext.Nasa -> "Лимит запросов NASA. Попробуйте позже или получите свой API-ключ на api.nasa.gov"
                ErrorContext.Iss -> "Сервер МКС временно недоступен"
            }
            else -> "Ошибка сервера: ${throwable.response.status.value}"
        }
        is ServerResponseException -> "Сервер не отвечает. Попробуйте позже"
        else -> {
            val msg = throwable.message ?: "Неизвестная ошибка"
            if (msg.contains("UnknownHost") || msg.contains("ConnectException"))
                "Нет интернета"
            else "Ошибка загрузки"
        }
    }
}