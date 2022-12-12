package com.zcf.kmmdemo.android

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zcf.kmmdemo.GreetingState
import com.zcf.kmmdemo.GreetingViewModel
import com.zcf.kmmdemo.PlatformInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Greeting() {
  val greetingViewModel = GreetingViewModel()

  val coroutineScope = rememberCoroutineScope()
  val scaffoldState = rememberScaffoldState()

  Scaffold(
    scaffoldState = scaffoldState
  ) { paddingValues ->
    Box(modifier = Modifier.padding(paddingValues)) {
      GreetingContent(greetingViewModel) { message ->
        coroutineScope.launch {
          scaffoldState.snackbarHostState.showSnackbar(message)
        }
      }
    }
  }
}

@Composable
fun GreetingContent(
  viewModel: GreetingViewModel,
  showSnackbar: (String) -> Unit
) {
  val greetingNumber by viewModel.greetingNumber.collectAsState(initial = -1)
  val state by viewModel.states.collectAsState(initial = GreetingState())

  val platformInfo = PlatformInfo().info()

  state.error?.let { errorMsg ->
    showSnackbar(errorMsg)
  }

  Column(modifier = Modifier.padding(16.dp)) {
    Text(text = "Hello, $platformInfo")
    Text("Repo number: $greetingNumber")

    Spacer(modifier = Modifier.height(20.dp))
    Text(text = "Users:", style = MaterialTheme.typography.h6)
    Spacer(modifier = Modifier.height(10.dp))
    Button(onClick = { viewModel.getUsers() }) {
      val text = if (state.users.isEmpty()) "Click to get users" else "Refresh users"
      Text(text = text)
    }
    val users = state.users
    if (users.isNotEmpty()) {
      Spacer(modifier = Modifier.height(10.dp))
      LazyColumn {
        items(
          count = users.size,
          key = { users[it].id }
        ) { index ->
          val user = users[index]
          Text("${user.id}, ${user.name}, ${user.email}")
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))
    Text(text = "Todos:", style = MaterialTheme.typography.h6)
    Spacer(modifier = Modifier.height(10.dp))
    val todos = state.todos
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
