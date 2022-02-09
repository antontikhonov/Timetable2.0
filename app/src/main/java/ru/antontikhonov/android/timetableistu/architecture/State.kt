package ru.antontikhonov.android.timetableistu.architecture

data class State<out T>(
    val content: T? = null,
    val loading: Boolean = false,
    val error: Throwable? = null
)

fun <T> stateContent(content: T?): State<T> = State(content = content)

fun <T> stateLoading(): State<T> = State(loading = true)

fun <T> stateError(error: Throwable): State<T> = State(error = error)
