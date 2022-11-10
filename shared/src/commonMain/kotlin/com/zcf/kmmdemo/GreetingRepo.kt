package com.zcf.kmmdemo

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GreetingRepo {

  fun numbers(): Flow<Int> {
    return flow {
      var number = 0
      while (number < 10) {
        println("zcf1 emit: $number")
        emit(number)
        number += 1
        delay(2000)
      }
    }
  }

  fun numbersCommonFlow() = numbers().asCommonFlow()

}