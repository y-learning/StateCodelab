package com.why.codelabs.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {
    private var _todoItems = MutableLiveData(listOf<TodoTask>())

    val todoItems: LiveData<List<TodoTask>> = _todoItems

    fun addItem(item: TodoTask) {
        _todoItems.value = _todoItems.value!! + listOf(item)
    }

    fun removeItem(item: TodoTask) {
        _todoItems.value = _todoItems.value!!.toMutableList().also {
            it.remove(item)
        }
    }
}
