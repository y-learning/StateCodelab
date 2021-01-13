package com.why.codelabs.state

import com.why.codelabs.state.view.util.generateTodoItem
import com.why.codelabs.state.viewmodels.TodoViewModel
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.types.shouldBeSameInstanceAs

class TodoViewModelTest : FreeSpec({
    "addItem(item) should add a TodoItem to todoItems." {
        val todoItem = generateTodoItem()
        val vm = TodoViewModel()

        vm.addItem(todoItem)

        vm.todoItems.count shouldBeExactly 1
        vm.todoItems[0] shouldBeSameInstanceAs todoItem
    }

    "removeItem(item)" {
        val vm = TodoViewModel()
        val todoItem1 = generateTodoItem()
        val todoItem2 = generateTodoItem()
        vm.addItem(todoItem1)
        vm.addItem(todoItem2)

        vm.removeItem(todoItem2)

        vm.todoItems.count shouldBeExactly 1
        vm.todoItems shouldContain todoItem1
    }
})
