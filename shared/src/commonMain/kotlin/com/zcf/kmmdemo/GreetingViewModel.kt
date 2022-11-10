package com.zcf.kmmdemo

import com.zcf.kmmdemo.api.JsonPlaceholderService
import com.zcf.kmmdemo.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GreetingViewModel: SharedViewModel() {

  val repo = GreetingRepo(JsonPlaceholderService())

  private val _greetingNumber = MutableStateFlow(-1)
  val greetingNumber: CommonFlow<Int> = _greetingNumber.asCommonFlow()

  private val _todos = MutableStateFlow(emptyList<Todo>())
  val todos: CommonFlow<List<Todo>> = _todos.asCommonFlow()

  init {
    sharedScope.launch {
      repo.greetingNumber().collectLatest { number ->
        _greetingNumber.update { number }
      }
    }
    sharedScope.launch {
      val todos = repo.getTodos()
      _todos.update { todos }
    }
  }
}