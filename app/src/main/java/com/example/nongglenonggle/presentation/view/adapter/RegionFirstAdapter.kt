package com.example.nongglenonggle.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nongglenonggle.R
import com.example.nongglenonggle.domain.entity.Model

class RegionFirstAdapter(private var items:List<Model.Location>): RecyclerView.Adapter<RegionFirstAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView){
        val location: TextView = itemView.findViewById(R.id.first_region)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionFirstAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.location_list1,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = items[position]
        holder.location.text = list.value
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateList(newData:List<Model.Location>){
        this.items = newData
        notifyDataSetChanged()
    }
}