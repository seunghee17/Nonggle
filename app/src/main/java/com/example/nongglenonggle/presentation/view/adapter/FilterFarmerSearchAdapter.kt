package com.example.nongglenonggle.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nongglenonggle.R
import com.example.nongglenonggle.domain.entity.OffererSearchFilterModel

class FilterFarmerSearchAdapter(
    private var items:List<OffererSearchFilterModel>,
    private val listener:onItemClickListener
):RecyclerView.Adapter<FilterFarmerSearchAdapter.ViewHolder>() {
    interface onItemClickListener{
        fun onItemClickListener(uid:String)
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val userInfo : TextView = itemView.findViewById(R.id.userInfo)
        val name : TextView = itemView.findViewById(R.id.name)
        val describe : TextView = itemView.findViewById(R.id.describe)
        val allCareer:TextView = itemView.findViewById(R.id.allcareer)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_farmersearch_filter,parent,false)
        return FilterFarmerSearchAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position < items.size){
            val item= items[position]
            val gender = item.userGender
            val age = item.userYear
            holder.userInfo.text = "${gender}∙ ${age}세"
            holder.name.text = "${item.userName} 일손"
            holder.describe.text = item.userPresent
            holder.allCareer.text = "경력 ${item.allCareer}"

            holder.itemView.setOnClickListener{
                listener.onItemClickListener(item.uid)
            }
            Glide.with(holder.image.context)
                .load(item.imageurl)
                .into(holder.image)
        }
    }

    fun updateList(newData:List<OffererSearchFilterModel>){
        items=newData
        notifyDataSetChanged()
    }
}