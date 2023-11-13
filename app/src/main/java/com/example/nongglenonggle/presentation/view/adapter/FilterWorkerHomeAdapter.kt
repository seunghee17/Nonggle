package com.example.nongglenonggle.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nongglenonggle.R
import com.example.nongglenonggle.domain.entity.OffererHomeFilterContent
import com.example.nongglenonggle.domain.entity.SeekerHomeFilterContent
import org.w3c.dom.Text

class FilterWorkerHomeAdapter(private var items:List<SeekerHomeFilterContent>):RecyclerView.Adapter<FilterWorkerHomeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.title)
        val firstAddress:TextView = itemView.findViewById(R.id.address)
        val deadline:TextView = itemView.findViewById(R.id.deadline)
        val workType : TextView = itemView.findViewById(R.id.type)
        val payment:TextView = itemView.findViewById(R.id.payment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_worker_home,parent,false)
        return FilterWorkerHomeAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position < items.size){
            val item= items[position]
            holder.title.text = item.title
            holder.firstAddress.text = item.firstAddress
            val detail = item.recruitPeriod.get("detail").toString()
            val type = item.recruitPeriod.get("type").toString()
            holder.deadline.text =
                if(detail == ""){
                    type
                }else{
                    detail
                }
            holder.payment.text = item.pay[0].toString()
        }
    }
    fun updateList(newData:List<SeekerHomeFilterContent>){
        items=newData
        notifyDataSetChanged()
    }
}