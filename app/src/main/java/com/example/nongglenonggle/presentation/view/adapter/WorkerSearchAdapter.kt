package com.example.nongglenonggle.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nongglenonggle.R
import com.example.nongglenonggle.domain.entity.NoticeContent
import com.example.nongglenonggle.domain.entity.WorkerFilterListData
import com.example.nongglenonggle.domain.entity.WorkerSearchRecommend

class WorkerSearchAdapter(
    private var items: MutableList<WorkerSearchRecommend> = mutableListOf(),
    private val listener:onItemClickListener
):RecyclerView.Adapter<WorkerSearchAdapter.ViewHolder>() {
    interface onItemClickListener{
        fun onItemClick(uid:String)
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val title:TextView = itemView.findViewById(R.id.title)
        val address:TextView = itemView.findViewById(R.id.address)
        val deadline : TextView = itemView.findViewById(R.id.deadline)
        val workType:TextView = itemView.findViewById(R.id.workType)
        val payType:TextView = itemView.findViewById(R.id.payType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_worker_filterlist,parent,false)
        return WorkerSearchAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position < items.size){
            val item = items[position]
            holder.title.text = item.title
            holder.address.text = item.firstAddress
            val detail = item.recruitPeriod.get("detail").toString()
            val type = item.recruitPeriod.get("type").toString()
            holder.deadline.text =
                if(detail == ""){
                    type
                }else{
                    detail
                }
            holder.workType.text = item.workType
            holder.payType.text = item.pay[0].toString()
            holder.itemView.setOnClickListener {
                listener.onItemClick(item.uid)
            }
        }
    }
    fun updateList(newData:List<WorkerSearchRecommend>){
        this.items.clear()
        this.items.addAll(newData)
        notifyDataSetChanged()
    }
}