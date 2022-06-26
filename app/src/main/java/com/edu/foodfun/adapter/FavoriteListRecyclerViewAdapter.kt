package com.edu.foodfun.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edu.foodfun.R
import com.edu.foodfun.SimpleRest


class FavoriteListRecyclerViewAdapter(private val context:Context, private val data:MutableList<SimpleRest>):RecyclerView.Adapter<FavoriteListRecyclerViewAdapter.SimpleRestViewHolder>(){
    class SimpleRestViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val restName: TextView =itemView.findViewById(R.id.txtRestName)
        val time: TextView =itemView.findViewById(R.id.txtTime)
        val btnDelete: Button =itemView.findViewById(R.id.btnDelete)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleRestViewHolder {
        return  SimpleRestViewHolder(LayoutInflater.from(context).inflate(R.layout.item_simple_favorite_list,parent,false))
    }
    override fun onBindViewHolder(holder: SimpleRestViewHolder, index: Int) {
        holder.apply {
            restName.text = data[index].restName
            time.text= data[index].time
            btnDelete.setOnClickListener{
                data.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
            }
        }
    }
    override fun getItemCount(): Int = data.size
}