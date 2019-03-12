package com.otb.sampleandroid

import com.otb.sampleandroid.api.QuestionApi
import com.otb.sampleandroid.data.Question
import com.otb.sampleandroid.data.QuestionAnswer

class SharedQuestionViewModel {
    private val api = QuestionApi(host(), port())
    var questions: List<Question> = emptyList()
    private val questionsWithAnswer = HashMap<Int, QuestionAnswer>()

    fun addAnswer(questionAnswer: QuestionAnswer) {
        questionsWithAnswer[questionAnswer.questionId] = questionAnswer
    }

    fun fetchQuestions(success: (List<Question>) -> Unit, failure: (Throwable?) -> Unit?) = api.getQuestions(
            success = {
                questions = it
                success(it)
            },
            failure = {
                println("error ${it?.message}")
            }
    )

    fun sendQuestionWithAnswer() {
        val listOfAnswers = questionsWithAnswer.values.toList()
        api.updateQuestions(listOfAnswers, success = {
            println("success")
        }, failure = {
            println("${it?.message}")
        })
    }

}