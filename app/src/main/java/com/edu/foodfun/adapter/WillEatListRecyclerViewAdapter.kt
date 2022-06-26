package com.edu.foodfun.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.edu.foodfun.R
import com.edu.foodfun.SimpleRest

class WillEatListRecyclerViewAdapter(private val context:Context, private val data:MutableList<SimpleRest>,private val simpleRestObserver: SimpleRestObserver):RecyclerView.Adapter<WillEatListRecyclerViewAdapter.SimpleRestViewHolder>(){
    class SimpleRestViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val restName: TextView = itemView.findViewById(R.id.txtRestName)
        val time: TextView = itemView.findViewById(R.id.txtTime)
        val frame: ConstraintLayout = itemView.findViewById(R.id.frameWillEat) 
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
        val btnAddFavarite: Button = itemView.findViewById(R.id.btnAddFavorite)
    }
    interface SimpleRestObserver{
        fun onFavorite(index: Int, simpleRest: SimpleRest)
        fun onItemClick(index: Int, simpleRest: SimpleRest)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleRestViewHolder {
        return  SimpleRestViewHolder(LayoutInflater.from(context).inflate(R.layout.item_simple_will_eat_rest,parent,false))
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: SimpleRestViewHolder, index: Int) {
        holder.apply {
            restName.text = data[index].restName
            time.text= data[index].time
            frame.setOnTouchListener { _, _ ->
                simpleRestObserver.onItemClick(holder.adapterPosition, data[index])
                true
            }
            btnDelete.setOnClickListener{
                data.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
            }
            btnAddFavarite.setOnClickListener {
                simpleRestObserver.onFavorite(holder.adapterPosition,data[index])
                data.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
            }
        }
    }
    override fun getItemCount(): Int = data.size
}