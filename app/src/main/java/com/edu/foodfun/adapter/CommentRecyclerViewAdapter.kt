package com.edu.foodfun.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edu.foodfun.R

class CommentRecyclerViewAdapter (private val context:Context, private val data: List<String>):RecyclerView.Adapter<CommentRecyclerViewAdapter.ItemCommentHolder>(){
    class ItemCommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtComment)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCommentHolder {
        return ItemCommentHolder(LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false))
    }
    override fun onBindViewHolder(holder: ItemCommentHolder, position: Int) {
        holder.apply{
            txtName.text = data[position]
        }
    }
    override fun getItemCount(): Int = data.size
}