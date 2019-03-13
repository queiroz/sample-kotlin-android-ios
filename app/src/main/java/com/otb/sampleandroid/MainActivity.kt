package com.otb.sampleandroid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
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
        questionAdapter = QuestionAdapter {
            questionViewModel.addAnswer(it)
        }

        list_questions.apply {
            adapter = questionAdapter
        }


        prev.setOnClickListener {
            val curItem = list_questions.currentItem
            if (curItem > 0) {
                list_questions.currentItem = curItem - 1
            }
        }
        list_questions.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position + 1 == questionAdapter.itemCount) {
                    next.text = "Finish"
                } else {
                    next.text = "Next"
                }
            }
        })
        next.setOnClickListener {
            val curItem = list_questions.currentItem
            val total = questionAdapter.itemCount
            if(curItem < total) {
                list_questions.currentItem = curItem + 1
            }
            if(curItem + 1 == total) {
                questionViewModel.sendQuestionWithAnswer()
                constraintLayoutThankYouText.visibility = View.VISIBLE
                next.visibility = View.GONE
                prev.visibility = View.GONE
            }
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
