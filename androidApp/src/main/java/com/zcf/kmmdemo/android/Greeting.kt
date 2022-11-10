package com.zcf.kmmdemo.android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zcf.kmmdemo.GreetingViewModel

@Composable
fun Greeting(
  text: String,
  viewModel: GreetingViewModel
) {
  val state by viewModel.state.collectAsState(initial = -1)
  val todos by viewModel.todos.collectAsState(initial = emptyList())

  Column {
    Text(text = text)
    Text("Repo number: $state")

    Spacer(modifier = Modifier.height(20.dp))
    LazyColumn {
      items(
        count = todos.size,
        key = { todos[it].id }
      ) { index ->
        val todo = todos[index]
        Text("${todo.id}. ${todo.title}, completed: ${todo.completed}")
      }
    }
  }
}
