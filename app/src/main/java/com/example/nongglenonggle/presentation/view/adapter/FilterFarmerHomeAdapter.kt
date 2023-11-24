package com.example.nongglenonggle.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nongglenonggle.R
import com.example.nongglenonggle.domain.entity.OffererHomeFilterContent

class FilterFarmerHomeAdapter(private var items: List<OffererHomeFilterContent>,
    private var listener:onItemClickListener):
    RecyclerView.Adapter<FilterFarmerHomeAdapter.ViewHolder>() {

    interface onItemClickListener{
        fun onItemClickListener(uid:String)
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val userName : TextView = itemView.findViewById(R.id.worker_name)
        val userGender:TextView = itemView.findViewById(R.id.gender)
        val userYear:TextView = itemView.findViewById(R.id.year)
        val allCareer : TextView = itemView.findViewById(R.id.careertotal)
        val profileImg:ImageView = itemView.findViewById(R.id.profile_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_farmer_home,parent,false)
        return FilterFarmerHomeAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position < items.size){
            val item = items[position]
            holder.userName.text = "${item.userName} 일손"
            holder.userGender.text = item.userGender
            holder.userYear.text = "${item.userYear}세"
            holder.allCareer.text = "경력 ${item.allCareer}"
            holder.itemView.setOnClickListener{
                listener.onItemClickListener(item.uid)
            }
            Glide.with(holder.profileImg.context)
                .load(item.imageurl)
                .error(R.drawable.bg_worker_home)
                .into(holder.profileImg)

        }

    }
    fun updateList(newData:List<OffererHomeFilterContent>){
        items=newData
        notifyDataSetChanged()
    }
}