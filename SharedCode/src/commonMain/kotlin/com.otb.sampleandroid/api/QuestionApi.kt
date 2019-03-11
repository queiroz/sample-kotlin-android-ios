package com.otb.sampleandroid.api

import com.otb.sampleandroid.data.Question
import com.otb.sampleandroid.data.QuestionAnswer
import com.otb.sampleandroid.util.ApplicationDispatcher
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

class QuestionApi {

    private val httpClient = HttpClient()
    private val url = "http://10.0.2.2:3000/"

    fun getQuestions(success: (List<Question>) -> Unit, failure: (Throwable?) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val url = "${url}questions"
                val json = httpClient.get<String>(url)
                Json.nonstrict.parse(Question.serializer().list, json)
                        .also(success)
            } catch (e: Exception) {
                failure(e)
            }
        }
    }

    fun updateQuestions(questions: List<QuestionAnswer>, success: () -> Unit, failure: (Throwable?) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                httpClient.post<List<QuestionAnswer>> {
                    url("${url}questions_answers")
                    body = TextContent(Json.stringify(QuestionAnswer.serializer().list, questions), contentType = ContentType.Application.Json)
                }
                success()
            } catch (e: Exception) {
                failure(e)
            }
        }
    }

}