package com.zcf.kmmdemo.api

import com.zcf.kmmdemo.model.Todo
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

  suspend fun getTodos(): List<Todo> {
    return try {
      val response = httpClient.get(URL_TODOS)
      response.body<List<Todo>>()
    } catch (e: Exception) {
      e.printStackTrace()
      emptyList()
    }
  }
}