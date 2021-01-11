package com.why.codelabs.state

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.why.codelabs.state.util.generateTodoItem

@Composable
fun TodoRow(
    todoTask: TodoTask,
    onItemClicked: (TodoTask) -> Unit,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier
        .clickable { onItemClicked(todoTask) }
        .padding(horizontal = 16.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween
) {
    Text(todoTask.title)
    Icon(todoTask.icon.imageVector)
}

@Composable
fun TodoScreen(
    todos: List<TodoTask>,
    onAddItem: (TodoTask) -> Unit,
    onRemoveItem: (TodoTask) -> Unit
) = Column {
    LazyColumn(
        modifier = Modifier.weight(1f),
        contentPadding = PaddingValues(top = 8.dp)
    ) {
        items(
            items = todos,
            itemContent = { todoTask ->
                TodoRow(
                    todoTask = todoTask,
                    onItemClicked = { onRemoveItem(it) },
                    modifier = Modifier.fillParentMaxWidth()
                )
            })
    }

    Button(
        onClick = { onAddItem(generateTodoItem()) },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Text("Add random item")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoRow() {
    val todoTask = remember { generateTodoItem() }
    TodoRow(
        todoTask = todoTask,
        onItemClicked = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        TodoTask("Learn compose", TodoIcon.Event),
        TodoTask("Take the codelab"),
        TodoTask("Apply state", TodoIcon.Done),
        TodoTask("Build dynamic UIs", TodoIcon.Square)
    )
    TodoScreen(items, {}, {})
}
