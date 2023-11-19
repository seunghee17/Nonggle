package com.example.nongglenonggle.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nongglenonggle.R
import com.example.nongglenonggle.domain.entity.ScoreDataModel

class ScoreForFarmerAdapter(
    private var items:List<ScoreDataModel> = listOf(),
    private val listener:ScoreForFarmerAdapter.onItemClickListener)
    :RecyclerView.Adapter<ScoreForFarmerAdapter.ViewHolder>(){
    interface onItemClickListener{
        fun onItemClick(item:ScoreDataModel)
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.name)
        val userInfo:TextView = itemView.findViewById(R.id.user_info)
        val success:TextView = itemView.findViewById(R.id.success)
        val fail:TextView = itemView.findViewById(R.id.fail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_for_score,parent,false)
        return ScoreForFarmerAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position < items.size){
            val item = items[position]
            holder.name.text = item.userName
            holder.userInfo.text = "${item.userGender} ∙ ${item.userYear}세"
            holder.fail.visibility = if (item.isSelected) View.GONE else View.VISIBLE
            holder.success.visibility = if (item.isSelected) View.VISIBLE else View.GONE
            holder.itemView.setOnClickListener{
                item.isSelected = !item.isSelected // 상태 토글
                notifyItemChanged(position) // 해당 아이템만 업데이트
                listener.onItemClick(item)
            }
        }
    }

    fun updateList(new:List<ScoreDataModel>){
        items=new
        notifyDataSetChanged()
    }
}