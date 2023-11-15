package com.example.nongglenonggle.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nongglenonggle.R
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.domain.entity.ResumeSummary

class ResumeAdapter(private var items: List<ResumeSummary>): RecyclerView.Adapter<ResumeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.title)
        val date: TextView = itemView.findViewById(R.id.date)
        val total : TextView = itemView.findViewById(R.id.total)
        val description:TextView = itemView.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResumeAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resume_writing,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResumeAdapter.ViewHolder, position: Int) {
        val list = items[position]
        holder.title.text = list.title
        holder.date.text = list.date
        holder.total.text = list.total
        holder.description.text = list.description
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateList(newData:List<ResumeSummary>){
        this.items = newData
        notifyDataSetChanged()
    }
}