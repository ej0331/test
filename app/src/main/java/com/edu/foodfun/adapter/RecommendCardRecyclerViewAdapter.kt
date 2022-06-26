package com.edu.foodfun.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edu.foodfun.R
import com.edu.foodfun.Recommend

class RecommendCardRecyclerViewAdapter (
        private val context : Context,
        private val data: List<Recommend>
):RecyclerView.Adapter<RecommendCardRecyclerViewAdapter.RecommendCardViewHolder>(){
        class RecommendCardViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
                var imageRest:ImageView = itemView.findViewById(R.id.imageRest)
                val txtName : TextView = itemView.findViewById(R.id.txtName)
                val txtType : TextView = itemView.findViewById(R.id.txtType)
                val txtAddress : TextView = itemView.findViewById(R.id.txtAddress)
                val txtTel : TextView = itemView.findViewById(R.id.txtTel)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendCardViewHolder {
                return  RecommendCardViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recommend_card,parent,false))
        }
        override fun onBindViewHolder(holder: RecommendCardViewHolder, position: Int) {
                holder.apply {
                        imageRest.setImageResource(data[position].image)
                        txtName.text = data[position].name
                        txtType.text = data[position].type
                        txtAddress.text = data[position].address
                        txtTel.text = data[position].tel
                }
        }
        override fun getItemCount(): Int = (data.size)
}