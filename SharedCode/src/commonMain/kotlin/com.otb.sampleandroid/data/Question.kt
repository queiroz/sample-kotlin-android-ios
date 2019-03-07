package com.otb.sampleandroid.data

import kotlinx.serialization.Serializable

@Serializable
data class Question(
        val id: Int,
        val name: String
)