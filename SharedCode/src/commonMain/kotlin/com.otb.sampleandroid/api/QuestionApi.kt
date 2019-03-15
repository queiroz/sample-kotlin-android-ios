package com.otb.sampleandroid.api

import com.otb.sampleandroid.data.Question
import com.otb.sampleandroid.data.QuestionAnswer
import com.otb.sampleandroid.data.QuestionAnswerData
import com.otb.sampleandroid.host
import com.otb.sampleandroid.util.ApplicationDispatcher
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

class QuestionApi {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer().apply {
                setMapper(Question::class, Question.serializer())
                setMapper(QuestionAnswerData::class, QuestionAnswerData.serializer())
                setMapper(QuestionAnswer::class, QuestionAnswer.serializer())
            }
        }
    }

    fun getQuestions(success: (List<Question>) -> Unit, failure: (Throwable?) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val url = "http://${host()}:3000/questions"
                val json = httpClient.get<String>(url)
                httpClient.close()
                Json.nonstrict.parse(Question.serializer().list, json)
                        .also(success)
            } catch (e: Exception) {
                failure(e)
            }
        }
    }

    fun updateQuestions(questions: List<QuestionAnswer>, success: (List<QuestionAnswer>) -> Unit, failure: (Throwable?) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val jsonRaw: String = Json.stringify(QuestionAnswer.serializer().list, questions)
                httpClient.post<String> {
                    url("http://${host()}:3000/answers")
                    body = jsonRaw
                }
//                val builder = buildRequest("answers", HttpMethod.Post, jsonRaw)
//                httpClient.request<String>(builder)
                httpClient.close()
                success(questions)
            } catch (e: Exception) {
                failure(e)
            }
        }
    }

    private fun buildRequest(endpoint: String, method: HttpMethod = HttpMethod.Get, body: String? = null): HttpRequestBuilder {
        val httpRequestBuilder = HttpRequestBuilder()
        httpRequestBuilder.contentType(ContentType.Application.Json)
        httpRequestBuilder.method = method
        httpRequestBuilder.url(
            scheme = "http",
            host = host(),
            port = 3000,
            path = endpoint
        )
        body?.let {
            httpRequestBuilder.body = it
        }
        return httpRequestBuilder
    }
}