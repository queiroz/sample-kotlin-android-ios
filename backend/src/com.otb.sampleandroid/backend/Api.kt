package com.otb.sampleandroid.backend

import com.otb.sampleandroid.data.QuestionAnswer
import io.ktor.application.call
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

internal fun Routing.api() {
    apiQuestions()
}

private fun Routing.apiQuestions() {
    route("questions") {
        get {
            val questions = ClassLoader.getSystemResource("questions.json").readText()
//            val questions = this.javaClass.classLoader.getResource("questions.json").readText()
            call.respond(TextContent(questions, ContentType.Application.Json))
        }
    }
    route("answers") {
        post {
            val answer = call.receive<QuestionAnswer>()
            println("received: $answer")
            call.respond(HttpStatusCode.OK)
        }
    }
}