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

  private val _state = MutableStateFlow(-1)
  val state: CommonFlow<Int> = _state.asCommonFlow()

  private val _todos = MutableStateFlow(emptyList<Todo>())
  val todos: CommonFlow<List<Todo>> = _todos.asCommonFlow()

  init {
    sharedScope.launch(context = Dispatchers.Default) {
      repo.numbers().collectLatest { number ->
        _state.update { number }
      }
    }
    sharedScope.launch(context = Dispatchers.Default) {
      val todos = repo.getTodos()
      _todos.update { todos }
    }
  }
}