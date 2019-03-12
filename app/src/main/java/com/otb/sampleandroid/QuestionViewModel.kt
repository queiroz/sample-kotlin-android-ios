package com.otb.sampleandroid

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.otb.sampleandroid.data.Question
import com.otb.sampleandroid.data.QuestionAnswer

class QuestionViewModel: ViewModel() {
    val questions = MutableLiveData<List<Question>>()
    private val sharedViewModel = SharedQuestionViewModel()

    fun addAnswer(questionAnswer: QuestionAnswer) {
        sharedViewModel.addAnswer(questionAnswer)
    }

    fun fetchQuestions() {
        sharedViewModel.fetchQuestions(
                success = {
                    questions.postValue(it)
                },
                failure = {
                    println(it)
                }
        )
    }

    fun sendQuestionWithAnswer() {
        sharedViewModel.sendQuestionWithAnswer()
    }

}