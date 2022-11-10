package com.zcf.kmmdemo

import com.zcf.kmmdemo.api.JsonPlaceholderService
import com.zcf.kmmdemo.model.Todo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GreetingRepo(
  val jsonPlaceholderService: JsonPlaceholderService
) {

  fun greetingNumber(): Flow<Int> {
    return flow {
      var number = 0
      while (number < 10) {
        emit(number)
        number += 1
        delay(2000)
      }
    }
  }

  fun greetingNumberCommonFlow() = greetingNumber().asCommonFlow()

  suspend fun getTodos(): List<Todo> {
    return jsonPlaceholderService.getTodos()
  }

}