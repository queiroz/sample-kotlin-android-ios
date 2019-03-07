package com.otb.sampleandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.otb.sampleandroid.adapters.QuestionAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAdapter()
//        questionAdapter.submitList(questions)
    }

    private fun setupAdapter() {
        questionAdapter = QuestionAdapter()
        list_questions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = questionAdapter
        }
    }

}
