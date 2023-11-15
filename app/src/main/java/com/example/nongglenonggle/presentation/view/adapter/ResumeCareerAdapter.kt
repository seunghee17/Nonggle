package com.example.nongglenonggle.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nongglenonggle.R
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.entity.ResumeSummary

class ResumeCareerAdapter(private var items: List<ResumeSummary>): RecyclerView.Adapter<ResumeCareerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val date : TextView = itemView.findViewById(R.id.dateperiod)
        val workTitle:TextView = itemView.findViewById(R.id.work_title)
        val workDescription:TextView = itemView.findViewById(R.id.work_description)
        val workTerm:TextView = itemView.findViewById(R.id.work_term)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_resume_item,parent, false)
        return ResumeCareerAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position < items.size){
            val list = items[position]
            holder.date.text = list.date
            holder.workTitle.text = list.title
            holder.workDescription.text = list.description
            holder.workTerm.text = list.total
        }
    }
    fun updatelist(newData:List<ResumeSummary>){
        items = newData.toMutableList()
        notifyDataSetChanged()
    }
}