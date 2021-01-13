package com.why.codelabs.state.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.whyrising.y.concretions.vector.PersistentVector
import com.github.whyrising.y.concretions.vector.toPvector
import com.github.whyrising.y.concretions.vector.v
import com.github.whyrising.y.vector.APersistentVector
import com.why.codelabs.state.view.TodoItem

class TodoViewModel : ViewModel() {
    private var _currentEditIndex by mutableStateOf(-1)

    val currentEditItem: TodoItem?
        get() = todoItems.valAt(_currentEditIndex)

    var todoItems by mutableStateOf(v<TodoItem>())
        private set

    fun setIndexOfSelectedItem(item: TodoItem) {
        _currentEditIndex = todoItems.indexOf(item)
    }

    fun resetCurrentEditIndex() {
        _currentEditIndex = -1
    }

    fun updateSelectedItem(item: TodoItem) {
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }

        todoItems = todoItems.assoc(
            _currentEditIndex,
            item
        ) as PersistentVector<TodoItem>
    }

    fun addItem(item: TodoItem) {
        todoItems = todoItems.conj(item)
    }

    fun removeItem(item: TodoItem) {
        fun removeTodoItemBy(index: Int) {
            // TODO: use concat function when it's available in y library
            val p1 = (todoItems.subvec(0, index) as APersistentVector<TodoItem>)
                .toPvector()
            val p2 = (todoItems.subvec(
                index + 1,
                todoItems.count
            ) as APersistentVector<TodoItem>)

            todoItems = p2.fold(p1) { acc, todoItem -> acc.conj(todoItem) }
        }

        val index = todoItems.indexOf(item)
        removeTodoItemBy(index)

        resetCurrentEditIndex()
    }
}
