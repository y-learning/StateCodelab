package com.why.codelabs.state.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.whyrising.y.concretions.map.m
import com.github.whyrising.y.map.IPersistentMap
import com.why.codelabs.state.view.TodoItem
import java.util.*

private val DEFAULT_UUID: UUID =
    UUID.fromString("11111111-1111-1111-1111-111111111111")

class TodoViewModel : ViewModel() {

    private var _currentEditIndex by mutableStateOf(DEFAULT_UUID)

    val currentEditItem: TodoItem?
        get() = todoItems.valAt(_currentEditIndex)

    //TODO: Replace with PersistentTreeMap when it's available in y library.
    var todoItems: IPersistentMap<UUID, TodoItem> by mutableStateOf(m())
        private set

    fun setIndexOfSelectedItem(item: TodoItem) {
        _currentEditIndex = item.id
    }

    fun resetCurrentEditIndex() {
        _currentEditIndex = DEFAULT_UUID
    }

    @ExperimentalStdlibApi
    fun updateSelectedItem(item: TodoItem) {
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }

        todoItems = todoItems.assoc(item.id, item)
    }

    @ExperimentalStdlibApi
    fun addItem(item: TodoItem) {
        todoItems = todoItems.assocNew(item.id, item)
    }

    fun removeItem(item: TodoItem) {
        todoItems = todoItems.dissoc(item.id)

        resetCurrentEditIndex()
    }
}
