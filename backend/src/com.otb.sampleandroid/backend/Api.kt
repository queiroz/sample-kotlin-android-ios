package com.otb.sampleandroid.backend

import com.otb.sampleandroid.data.QuestionAnswer
import io.ktor.application.call
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import java.io.File

internal fun Routing.api() {
    apiQuestions()
}

private fun Routing.apiQuestions() {
    route("questions") {
        get {
            val questions = ClassLoader.getSystemResource("questions.json").readText()
            call.respond(TextContent(questions, ContentType.Application.Json))
        }
    }
    route("answers") {
        post {
            val answersRaw = call.receiveText()
            val newAnwers: List<QuestionAnswer> = Json.parse(QuestionAnswer.serializer().list, answersRaw)
            val file = File("backend/resources/answers.json")
            val currentAnswers: List<QuestionAnswer> = Json.parse(QuestionAnswer.serializer().list, file.readText())
            val newListOfAnswers = mutableListOf<QuestionAnswer>()
            newListOfAnswers.addAll(currentAnswers)
            newListOfAnswers.addAll(newAnwers)
            val stringAmendedAnswers = Json.stringify(QuestionAnswer.serializer().list, newListOfAnswers)
            file.writeText(stringAmendedAnswers)
            call.respond(HttpStatusCode.OK)
        }
    }
}