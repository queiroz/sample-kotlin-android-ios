package com.otb.sampleandroid.data

import kotlinx.serialization.Serializable

@Serializable
data class QuestionData(
    val questions: List<Question>
)