package com.otb.sampleandroid

import com.otb.sampleandroid.api.QuestionApi

expect fun platformName(): String

fun getQuestionApi() : QuestionApi = QuestionApi()

