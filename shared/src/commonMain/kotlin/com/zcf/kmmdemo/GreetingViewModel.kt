package com.zcf.kmmdemo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GreetingViewModel: SharedViewModel() {

  val repo = GreetingRepo()

  private val _state = MutableStateFlow(-1)
  val state: CommonFlow<Int> = _state.asCommonFlow()

  init {
    sharedScope.launch(context = Dispatchers.Default) {
      repo.numbers().collectLatest { number ->
        _state.update { number }
      }
    }
  }
}