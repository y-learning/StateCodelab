package com.why.codelabs.state.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import com.github.whyrising.y.concretions.list.ASeq
import com.why.codelabs.state.theme.StateCodelabTheme
import com.why.codelabs.state.viewmodels.TodoViewModel

@ExperimentalStdlibApi
@ExperimentalAnimationApi
@Composable
fun Screen(vm: TodoViewModel) {
    TodoScreen(
        todos = vm.todoItems.vals() as ASeq<TodoItem>,
        vm.currentEditItem,
        onAddItem = vm::addItem,
        onRemoveItem = vm::removeItem,
        onStartEdit = vm::setIndexOfSelectedItem,
        onEditItemChanged = vm::updateSelectedItem,
        onEditDone = vm::resetCurrentEditIndex,
    )
}

class TodoActivity : AppCompatActivity() {
    private val todoViewModel by viewModels<TodoViewModel>()

    @ExperimentalStdlibApi
    @ExperimentalAnimationApi
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
