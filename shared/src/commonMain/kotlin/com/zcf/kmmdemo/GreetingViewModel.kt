package com.zcf.kmmdemo

import com.zcf.kmmdemo.api.JsonPlaceholderService
import com.zcf.kmmdemo.model.Todo
import com.zcf.kmmdemo.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GreetingState(
  val todos: List<Todo> = emptyList(),
  val users: List<User> = emptyList(),
  val error: String = "Test error!"
)

class GreetingViewModel: SharedViewModel() {

  val repo = GreetingRepo(JsonPlaceholderService())

  private val _greetingNumber = MutableStateFlow(-1)
  val greetingNumber: CommonFlow<Int> = _greetingNumber.asCommonFlow()

  private val _states = MutableStateFlow(GreetingState())
  val states : CommonFlow<GreetingState> = _states.asCommonFlow()

  init {
    sharedScope.launch {
      repo.greetingNumber().collectLatest { number ->
        _greetingNumber.update { number }
      }
    }
    sharedScope.launch {
      repo.getTodos()
        .onSuccess { todos ->
          _states.update { it.copy(todos = todos, error = "") }
        }
        .onFailure { throwable ->
          _states.update { it.copy(todos = emptyList(), error = throwable.message ?: "Get todos failed!") }
        }
    }
  }

  fun getUsers() {
    sharedScope.launch {
      repo.getUsers()
        .onSuccess { users ->
          _states.update { it.copy(users = users, error = "") }
        }
        .onFailure { throwable ->
          _states.update { it.copy(users = emptyList(), error = throwable.message ?: "Get users failed!") }
        }
    }
  }

  fun clearError() {
    _states.update { it.copy(error = "") }
  }
}