package com.capstone.nongglenonggle.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.domain.entity.AlarmDataModel

class AlarmAdapter(
    private var items:List<AlarmDataModel> = listOf(),
    private val listener:onItemClickListener
) : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    interface onItemClickListener{
        fun onItemClick(item:AlarmDataModel)
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val info: TextView = itemView.findViewById(R.id.info)
        val date : TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm_list,parent,false)
        return AlarmAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position<items.size){
            val item= items[position]
            holder.info.text = "${item.offererName} 구인자님이 채용제안을 보냈어요. 확인해보세요!"
            holder.date.text = item.currentTime

            holder.itemView.setOnClickListener{
                //채용제안 수락 완료 처리
                listener.onItemClick(item)
            }
        }
    }


    fun updateList(newData:List<AlarmDataModel>){
        items=newData
        notifyDataSetChanged()
    }

}