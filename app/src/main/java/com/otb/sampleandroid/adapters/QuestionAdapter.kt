package com.otb.sampleandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.otb.sampleandroid.data.Question
import com.otb.sampleandroid.data.QuestionAnswer
import com.otb.sampleandroid.databinding.QuestionItemBinding
import kotlinx.android.synthetic.main.question_item.view.*
import android.util.TypedValue
import android.widget.ImageView
import androidx.core.view.children
import com.otb.sampleandroid.R

class QuestionAdapter(
        private val onQuestion: (QuestionAnswer) -> Unit,
        private val onPage: (Int) -> Unit,
        private val onFinish: () -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer<Question>(this, DIFF_ITEM_CALLBACK)
    private var selectedFace: Int = 0

    fun submitList(questions: List<Question>) = differ.submitList(questions)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val questionBinding = QuestionItemBinding.inflate(layoutInflater, parent, false)
        return QuestionViewHolder(questionBinding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is QuestionViewHolder) holder.bind(differ.currentList[position])
    }

    internal inner class QuestionViewHolder(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(question: Question) = with(binding.root) {
            binding.setVariable(BR.viewmodel, question)
            binding.executePendingBindings()
            val views = question_item.children
            for (v in views) {
                if (v is ImageView) {
                    v.setOnClickListener {
                        for (vv in question_item.children) {
                            if (vv is ImageView) {
                                selectFace(vv, 32F)
                            }
                        }
                        selectFace(v, 60F)
                        when (v.id) {
                            R.id.insane -> {
                                generateAnswer(question, 0)
                            }
                            R.id.sad -> {
                                generateAnswer(question, 1)
                            }
                            R.id.happy -> {
                                generateAnswer(question, 2)
                            }
                            R.id.super_happy -> {
                                generateAnswer(question, 3)
                            }
                        }
                    }
                }
            }
            val page = adapterPosition + 1
            if (page == differ.currentList.size) {
                next.text = context.getText(com.otb.sampleandroid.R.string.submit)
            } else {
                next.text = context.getText(com.otb.sampleandroid.R.string.next)
            }
            next.setOnClickListener {
                if (page < differ.currentList.size) {
                    onPage(adapterPosition + 1)
                } else {
                    onFinish()
                }
            }
            back.setOnClickListener {
                if (page >= 0) onPage(adapterPosition - 1)
            }
        }

        private fun generateAnswer(question: Question, answer: Int) {
            val q = QuestionAnswer(question.id, answer)
            onQuestion(q)
        }

        private fun selectFace(view: View, size: Float) {
            if (selectedFace == view.id) {
                selectedFace = 0
            }
            selectedFace = view.id
            val r = view.resources
            val px = Math.round(TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, size, r.displayMetrics))
            val layoutParams = view.layoutParams
            layoutParams.width = px
            layoutParams.height = px
            view.layoutParams = layoutParams
        }

    }

    companion object {
        val DIFF_ITEM_CALLBACK = object: DiffUtil.ItemCallback<Question>() {
            override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
                return oldItem == newItem
            }
        }
    }
}