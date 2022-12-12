package com.zcf.kmmdemo.model

@kotlinx.serialization.Serializable
data class User(
  val id: Long,
  val name: String,
  val email: String
)