package com.otb.sampleandroid.api

import com.otb.sampleandroid.data.Question
import com.otb.sampleandroid.data.QuestionAnswer
import com.otb.sampleandroid.util.ApplicationDispatcher
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.*
import io.ktor.client.response.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.URLBuilder
import io.ktor.http.contentType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

class QuestionApi(
        private val host: String,
        private val port: Int
) {

    //    private val httpClient = HttpClient()
    val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer().apply {
                setMapper(Question::class, Question.serializer())
                setMapper(QuestionAnswer::class, QuestionAnswer.serializer())
                setMapper(QuestionList::class, QuestionList.serializer())
            }
        }
    }

    fun getQuestions(success: (List<Question>) -> Unit, failure: (Throwable?) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val url = "http://$host:$port/questions"
                val json = httpClient.get<String>(url)
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
                val questionData = QuestionList(questions)
                val builder = buildRequest("questions_answers", HttpMethod.Post, questionData)
                httpClient.request<HttpResponse>(builder)
                success(questions)
            } catch (e: Exception) {
                failure(e)
            }
        }
    }

    private fun buildRequest(endpoint: String, method: HttpMethod = HttpMethod.Get, body: Any? = null): HttpRequestBuilder {
        val httpRequestBuilder = HttpRequestBuilder()
        httpRequestBuilder.contentType(ContentType.Application.Json)
        httpRequestBuilder.header("Content-Type", "application/json")
        httpRequestBuilder.method = method
        httpRequestBuilder.host = host
        httpRequestBuilder.port = port
        httpRequestBuilder.url { URLBuilder(endpoint) }
        body?.let {
            httpRequestBuilder.body = it
        }
        return httpRequestBuilder
    }
}


@Serializable
data class QuestionList(
        val questionList: List<QuestionAnswer>
)

@Serializable
data class QuestionData(
        val questions: List<Question>
)