package com.example.nongglenonggle.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nongglenonggle.R
import com.example.nongglenonggle.domain.entity.OffererHomeFilterContent
import com.example.nongglenonggle.domain.entity.SeekerHomeFilterContent
import org.w3c.dom.Text

class FilterWorkerHomeAdapter(
    private var items:List<SeekerHomeFilterContent>,
    private val listener: onItemClickListener
)
    :RecyclerView.Adapter<FilterWorkerHomeAdapter.ViewHolder>() {
    interface onItemClickListener{
        fun onItemClick(uid: String)
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.title)
        val firstAddress:TextView = itemView.findViewById(R.id.address)
        val deadline:TextView = itemView.findViewById(R.id.deadline)
        val workType : TextView = itemView.findViewById(R.id.type)
        val payment:TextView = itemView.findViewById(R.id.payment)
        val image: ImageView = itemView.findViewById(R.id.image)
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
            holder.workType.text = item.workType
            val detail = item.recruitPeriod.get("detail").toString()
            val type = item.recruitPeriod.get("type").toString()
            holder.deadline.text =
                if(detail == ""){
                    type
                }else{
                    detail
                }
            holder.payment.text = item.pay[0].toString()
            holder.itemView.setOnClickListener {
                listener.onItemClick(item.uid)
            }
            Glide.with(holder.image.context)
                .load(item.imageUrl)
                .error(R.drawable.bg_farmer_home)
                .into(holder.image)

        }
    }
    fun updateList(newData:List<SeekerHomeFilterContent>){
        items=newData
        notifyDataSetChanged()
    }
}