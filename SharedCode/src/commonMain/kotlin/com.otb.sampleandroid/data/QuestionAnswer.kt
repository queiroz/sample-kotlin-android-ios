package com.otb.sampleandroid.data

import kotlinx.serialization.Serializable

@Serializable
data class QuestionAnswer(
        val questionId: Int,
        val questionAnswer: Int
)