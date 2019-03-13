package com.otb.sampleandroid

import com.otb.sampleandroid.api.QuestionApi
import com.otb.sampleandroid.data.Question
import com.otb.sampleandroid.data.QuestionAnswer

class SharedQuestionViewModel {

    private var questions: List<Question> = emptyList()
    private val api = QuestionApi()
    private val _answers = HashMap<Int, QuestionAnswer>()
    val answers: Map<Int, QuestionAnswer>
        get() = _answers

    fun addAnswer(questionAnswer: QuestionAnswer) {
        _answers[questionAnswer.questionId] = questionAnswer
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
        val listOfAnswers = answers.values.toList()
        api.updateQuestions(listOfAnswers, success = {
            println("success")
        }, failure = {
            println("${it?.message}")
        })
    }

}