package com.why.codelabs.state.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import com.why.codelabs.state.theme.StateCodelabTheme
import com.why.codelabs.state.viewmodels.TodoViewModel

@Composable
fun Screen(vm: TodoViewModel) {
    val todos: List<TodoItem> by vm.todoItems.observeAsState(listOf())
    TodoScreen(
        todos = todos,
        onAddItem = vm::addItem,
        onRemoveItem = vm::removeItem
    )
}

class TodoActivity : AppCompatActivity() {
    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StateCodelabTheme {
                Surface {
                    Screen(todoViewModel)
                }
            }
        }
    }
}
