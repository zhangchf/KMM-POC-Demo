package com.zcf.kmmdemo.api

import com.zcf.kmmdemo.model.Todo
import com.zcf.kmmdemo.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class JsonPlaceholderService {
  companion object {
    const val URL_TODOS = "https://jsonplaceholder.typicode.com/todos"
    const val URL_USERS = "https://jsonplaceholder.typicode.com/users"
  }

  private val httpClient = HttpClient {
    install(ContentNegotiation) {
      json(Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
      })
    }
  }

  suspend fun getTodos(): Result<List<Todo>> {
    return try {
      val response = httpClient.get(URL_TODOS)
      Result.success(response.body())
    } catch (e: Exception) {
      e.printStackTrace()
      Result.failure(e)
    }
  }

  suspend fun getUsers(): Result<List<User>> {
    return try {
      val response = httpClient.get(URL_USERS)
      Result.success(response.body())
    } catch (e: Exception) {
      e.printStackTrace()
      Result.failure(e)
    }
  }
}