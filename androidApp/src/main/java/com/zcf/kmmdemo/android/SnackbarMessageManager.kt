package com.zcf.kmmdemo.android

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

data class Message(val id: Long, val message: String)

object SnackbarMessageManager {
  private val _messages: MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())
  private val message: StateFlow<List<Message>> = _messages

  fun showMessage(message: String) {
    _messages.update { currentMessages ->
      currentMessages + Message(
        id = UUID.randomUUID().mostSignificantBits,
        message = message
      )
    }
  }

  fun setMessageShown(messageId: Long) {
    _messages.update { currentMessages ->
      currentMessages.filterNot { it.id == messageId }
    }
  }
}