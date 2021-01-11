package com.why.codelabs.state

import java.util.*

data class TodoTask(
    val title: String,
    val icon: TodoIcon = TodoIcon.Default,
    val id: UUID = UUID.randomUUID()
)
