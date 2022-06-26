package com.edu.foodfun.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edu.foodfun.R

class StrangerRecyclerViewAdapter (private val context: Context, private val data: List<String>): RecyclerView.Adapter<StrangerRecyclerViewAdapter.ItemContactCardHolder>() {
    class ItemContactCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtName)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemContactCardHolder {
        return ItemContactCardHolder(LayoutInflater.from(context).inflate(R.layout.item_contact_card, parent, false))
    }
    override fun onBindViewHolder(holder: ItemContactCardHolder, position: Int) {
        holder.apply {
            txtName.text = data[position]
        }
    }
    override fun getItemCount(): Int = data.size
}