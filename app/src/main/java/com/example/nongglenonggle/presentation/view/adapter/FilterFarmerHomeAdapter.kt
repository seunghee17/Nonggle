package com.example.nongglenonggle.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nongglenonggle.R
import com.example.nongglenonggle.domain.entity.OffererHomeFilterContent

class FilterFarmerHomeAdapter(private var items: List<OffererHomeFilterContent>): RecyclerView.Adapter<FilterFarmerHomeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val userName : TextView = itemView.findViewById(R.id.worker_name)
        val userGender:TextView = itemView.findViewById(R.id.gender)
        val userYear:TextView = itemView.findViewById(R.id.year)
        val allCareer : TextView = itemView.findViewById(R.id.careertotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_farmer_home,parent,false)
        return FilterFarmerHomeAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = items[position]
        holder.userName.text = "${list.userName} 일손"
        holder.userGender.text = list.userGender
        holder.userYear.text = "${list.userYear}세"
        holder.allCareer.text = "경력 ${list.allCareer}"

    }
    fun updateList(newData:List<OffererHomeFilterContent>){
        items=newData
        notifyDataSetChanged()
    }
}