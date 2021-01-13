package com.why.codelabs.state.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AmbientContentColor
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.why.codelabs.state.view.util.generateTodoItem
import com.why.codelabs.state.view.util.randomTint

const val DEFAULT_INPUT = ""
val DEFAULT_ICON = TodoIcon.Default

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

@ExperimentalAnimationApi
@Composable
fun TodoItemInput(
    text: String,
    userInputFilled: Boolean,
    icon: TodoIcon,
    addTodoItem: () -> Unit,
    setText: (String) -> Unit,
    setIcon: (TodoIcon) -> Unit,
    actions: @Composable () -> Unit
) = Column {
    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        TodoInputText(
            text = text,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            onImeAction = addTodoItem,
            onTextChange = setText
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(Modifier.align(Alignment.CenterVertically)) { actions() }
    }
    when {
        userInputFilled -> AnimatedIconRow(
            Modifier.padding(top = 8.dp),
            selectedIcon = icon,
            onIconSelected = setIcon
        )
        else -> Spacer(modifier = Modifier.preferredHeight(16.dp))
    }
}

@ExperimentalAnimationApi
@Composable
fun TodoItemInlineEditor(
    todoItem: TodoItem,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit,
    onRemoveItem: () -> Unit
) = TodoItemInput(
    text = todoItem.text,
    addTodoItem = onEditDone,
    setText = { onEditItemChange(todoItem.copy(text = it)) },
    userInputFilled = true,
    icon = todoItem.icon,
    setIcon = { onEditItemChange(todoItem.copy(icon = it)) }) {
    Row {
        val shrinkButtons = Modifier.widthIn(20.dp)
        TextButton(onClick = onEditDone, modifier = shrinkButtons) {
            Text(
                text = "\uD83D\uDCBE",
                textAlign = TextAlign.End,
                modifier = Modifier.width(30.dp)
            )
        }
        TextButton(onClick = onRemoveItem, modifier = shrinkButtons) {
            Text(
                text = "❌",
                textAlign = TextAlign.End,
                modifier = Modifier.width(30.dp)
            )
        }
    }
}

@Composable
fun TodoRow(
    todoItem: TodoItem,
    onItemClicked: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier
        .clickable { onItemClicked(todoItem) }
        .padding(horizontal = 16.dp, vertical = 8.dp),
) {
    Text(todoItem.text, modifier = Modifier.weight(1f))
    val iconAlpha = remember(todoItem.id) { randomTint() }
    Icon(
        todoItem.icon.imageVector,
        tint = AmbientContentColor.current.copy(alpha = iconAlpha),
    )
}

@ExperimentalAnimationApi
@Composable
fun TodoItemInputState(onAddTodoItem: (TodoItem) -> Unit) {
    val (text, setText) = remember { mutableStateOf(DEFAULT_INPUT) }
    val (icon, setIcon) = remember { mutableStateOf(DEFAULT_ICON) }
    val userInputFilled = text.isNotBlank()
    fun resetInternalState() {
        setText(DEFAULT_INPUT)
        setIcon(DEFAULT_ICON)
    }

    val addTodoItem = {
        if (userInputFilled) {
            onAddTodoItem(TodoItem(text, icon))
            resetInternalState()
        }
    }
    TodoItemInput(
        text = text,
        addTodoItem = addTodoItem,
        setText = setText,
        userInputFilled = userInputFilled,
        icon = icon,
        setIcon = setIcon
    ) {
        TodoEditButton(
            "Add",
            enabled = userInputFilled,
            onClick = addTodoItem
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun ScreenHeader(
    currentlyEditItem: TodoItem?,
    onAddItem: (TodoItem) -> Unit
) = TodoItemInputBackground(
    elevate = true,
    modifier = Modifier.fillMaxWidth()
) {
    when (currentlyEditItem) {
        null -> TodoItemInputState(onAddTodoItem = onAddItem)
        else -> Text(
            "Editing item",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun TodoScreen(
    todos: List<TodoItem>,
    currentlyEditItem: TodoItem?,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit,
    onStartEdit: (TodoItem) -> Unit,
    onEditItemChanged: (TodoItem) -> Unit,
    onEditDone: () -> Unit
) = Column {
    ScreenHeader(currentlyEditItem, onAddItem)
    LazyColumn(
        modifier = Modifier.weight(1f),
        contentPadding = PaddingValues(top = 8.dp)
    ) {
        items(items = todos) { todoItem ->
            when (currentlyEditItem?.id) {
                todoItem.id -> TodoItemInlineEditor(
                    todoItem = todoItem,
                    onEditItemChange = onEditItemChanged,
                    onEditDone = onEditDone,
                    onRemoveItem = { onRemoveItem(todoItem) })
                else -> TodoRow(
                    todoItem = todoItem,
                    onItemClicked = { onStartEdit(it) },
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
        }
    }

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

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun PreviewScreenHeader1() {
    ScreenHeader(null) { }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun PreviewScreenHeader2() {
    ScreenHeader(generateTodoItem()) { }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun PreviewTodoItemInlineEditor() {
    TodoItemInlineEditor(
        todoItem = generateTodoItem(),
        onEditItemChange = { /*TODO*/ },
        onEditDone = { /*TODO*/ },
        onRemoveItem = { /*TODO*/ })
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        TodoItem("Learn compose", TodoIcon.Event),
        TodoItem("Take the codelab"),
        TodoItem("Apply state", TodoIcon.Done),
        TodoItem("Build dynamic UIs", TodoIcon.Square)
    )
    TodoScreen(items, null, {}, {}, {}, {}, {})
}
