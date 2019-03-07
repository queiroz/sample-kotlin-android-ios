package com.otb.sampleandroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.otb.sampleandroid.data.Question
import com.otb.sampleandroid.databinding.QuestionItemBinding

class QuestionAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer<Question>(this, DIFF_ITEM_CALLBACK)

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

        fun bind(question: Question) {
            binding.setVariable(BR.viewmodel, question)
            binding.executePendingBindings()
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