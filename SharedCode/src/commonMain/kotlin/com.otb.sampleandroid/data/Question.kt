package com.otb.sampleandroid.data

import kotlinx.serialization.Serializable

@Serializable
data class Question(
        var id: Int,
        var name: String
)