package com.why.codelabs.state.view

import java.util.*

data class TodoItem(
    val text: String,
    val icon: TodoIcon = TodoIcon.Default,
    val id: UUID = UUID.randomUUID()
)
