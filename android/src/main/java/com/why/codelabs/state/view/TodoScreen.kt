package com.why.codelabs.state.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AmbientContentColor
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.why.codelabs.state.view.util.generateTodoItem
import com.why.codelabs.state.view.util.randomTint

@Composable
fun TodoRow(
    todoItem: TodoItem,
    onItemClicked: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier
        .clickable { onItemClicked(todoItem) }
        .padding(horizontal = 16.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween
) {
    Text(todoItem.text)
    val iconAlpha = remember(todoItem.id) { randomTint() }
    Icon(
        todoItem.icon.imageVector,
        tint = AmbientContentColor.current.copy(alpha = iconAlpha)
    )
}

@Composable
fun AddRandomTaskButton(onAddItem: (TodoItem) -> Unit) {
    Button(
        onClick = { onAddItem(generateTodoItem()) },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Text("ADD RANDOM TASK")
    }
}

@Composable
private fun TodoList(
    modifier: Modifier = Modifier,
    todos: List<TodoItem>,
    onRemoveItem: (TodoItem) -> Unit
) = LazyColumn(
    modifier = modifier,
    contentPadding = PaddingValues(top = 8.dp)
) {
    items(todos) { todoTask ->
        TodoRow(
            todoItem = todoTask,
            onItemClicked = { onRemoveItem(it) },
            modifier = Modifier.fillParentMaxWidth()
        )
    }
}

@Composable
fun TodoScreen(
    todos: List<TodoItem>,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit
) = Column {
    TodoList(
        modifier = Modifier.weight(1f),
        todos = todos,
        onRemoveItem = onRemoveItem
    )

    AddRandomTaskButton(onAddItem)
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoRow() {
    val todoTask = remember { generateTodoItem() }
    TodoRow(
        todoItem = todoTask,
        onItemClicked = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        TodoItem("Learn compose", TodoIcon.Event),
        TodoItem("Take the codelab"),
        TodoItem("Apply state", TodoIcon.Done),
        TodoItem("Build dynamic UIs", TodoIcon.Square)
    )
    TodoScreen(items, {}, {})
}
