package com.otb.sampleandroid

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.otb.sampleandroid.api.QuestionApi
import com.otb.sampleandroid.data.Question
import com.otb.sampleandroid.data.QuestionAnswer

class QuestionViewModel: ViewModel() {
    private val api by lazy { QuestionApi() }

    val questions = MutableLiveData<List<Question>>()
    private val questionsWithAnswer = HashMap<Int, QuestionAnswer>()

    fun addAnswer(questionAnswer: QuestionAnswer) {
        questionsWithAnswer[questionAnswer.questionId] = questionAnswer
    }

    fun fetchQuestions() = api.getQuestions(
            success = {
                questions.postValue(it)
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