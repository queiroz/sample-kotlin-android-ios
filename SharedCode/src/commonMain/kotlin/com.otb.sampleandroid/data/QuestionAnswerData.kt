package com.otb.sampleandroid.data

import kotlinx.serialization.Serializable

@Serializable
data class QuestionAnswerData(
        val answers: List<QuestionAnswer>
)