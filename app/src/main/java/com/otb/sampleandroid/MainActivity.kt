package com.otb.sampleandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.otb.sampleandroid.adapters.QuestionAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var questionViewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel::class.java)
        setupAdapterWithViewPager()
        initialize()

    }

    private fun setupAdapterWithViewPager() {
        questionAdapter = QuestionAdapter(onQuestion = {
            questionViewModel.addAnswer(it)
        }, onPage = {
            list_questions.currentItem = it
        }, onFinish = {
            questionViewModel.sendQuestionWithAnswer()
            println("sent")
        })

        list_questions.apply {
            adapter = questionAdapter
        }
    }

    private fun initialize() {
        questionViewModel.questions.observe(this, Observer {
            println(it)
            questionAdapter.submitList(it)
        })
        questionViewModel.fetchQuestions()
    }

}
