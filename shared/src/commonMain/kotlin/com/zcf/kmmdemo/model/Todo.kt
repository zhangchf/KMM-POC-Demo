package com.zcf.kmmdemo.model

import kotlinx.serialization.Serializable

/**
{
"userId": 1,
"id": 3,
"title": "fugiat veniam minus",
"completed": false
}
 */
@Serializable
data class Todo(
  val userId: Long,
  val id: Long,
  val title: String,
  val completed: Boolean
)