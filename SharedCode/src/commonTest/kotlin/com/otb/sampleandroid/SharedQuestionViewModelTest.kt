package com.otb.sampleandroid

import com.otb.sampleandroid.data.QuestionAnswer
import kotlin.test.Test
import kotlin.test.assertEquals

class SharedQuestionViewModelTest {

    private val sharedQuestionViewModel: SharedQuestionViewModel = SharedQuestionViewModel()

    @Test
    fun addAnswerPopulatesAnswersHashMap() {
        val questionAnswer = QuestionAnswer(1, 2)
        val expectedAnswers = HashMap<Int, QuestionAnswer>()
        expectedAnswers[questionAnswer.questionId] = questionAnswer
        sharedQuestionViewModel.addAnswer(questionAnswer)
        assertEquals(expectedAnswers, sharedQuestionViewModel.answers)
    }

}