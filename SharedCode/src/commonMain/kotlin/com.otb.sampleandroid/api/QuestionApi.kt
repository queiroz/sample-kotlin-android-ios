package com.otb.sampleandroid.api

import com.otb.sampleandroid.data.Question
import com.otb.sampleandroid.util.ApplicationDispatcher
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

class QuestionApi {

    private val httpClient = HttpClient()

    fun getQuestions(success: (List<Question>) -> Unit, failure: (Throwable?) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val url = "http://10.0.2.2:3000/questions"
                val json = httpClient.get<String>(url)
                Json.nonstrict.parse(Question.serializer().list, json)
                        .also(success)
            } catch (e: Exception) {
                failure(e)
            }
        }
    }

}