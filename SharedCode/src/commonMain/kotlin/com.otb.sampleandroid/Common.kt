package com.otb.sampleandroid

expect fun platformName(): String

fun createApplicationScreenMessage() : String {
//    var response: QuestionData
//    GlobalScope.launch {
//        val client = HttpClient {
//            install(JsonFeature) {
//                serializer = KotlinxSerializer()
//            }
//        }
//        response = client.request {
//            url("http://localhost:3000")
//            method = HttpMethod.Get
//        }
//
//    }
//    return "Kotlin Rocks on ${platformName()} $response"
    return "a"
}