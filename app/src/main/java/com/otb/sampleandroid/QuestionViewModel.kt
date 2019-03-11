package com.otb.sampleandroid

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.otb.sampleandroid.api.QuestionApi
import com.otb.sampleandroid.data.Question

class QuestionViewModel: ViewModel() {
    private val api by lazy { QuestionApi() }

    val questions = MutableLiveData<List<Question>>()

    fun fetchQuestions() = api.getQuestions(
            success = {
                questions.postValue(it)
            },
            failure = {
                println("error ${it?.message}")
            }
    )

}