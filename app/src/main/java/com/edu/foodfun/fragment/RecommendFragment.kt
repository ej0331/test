package com.edu.foodfun.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.foodfun.R
import com.edu.foodfun.Recommend
import com.edu.foodfun.adapter.CommentRecyclerViewAdapter
import com.edu.foodfun.adapter.RecommendCardRecyclerViewAdapter
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback
import me.yuqirong.cardswipelayout.CardLayoutManager
import me.yuqirong.cardswipelayout.OnSwipeListener

class RecommendFragment : Fragment() {
    private lateinit var recyclerRecommendCardList: RecyclerView
    private lateinit var recyclerCommentList: RecyclerView
    private val recommendCardData : MutableList<Recommend> = arrayListOf()
    private val commentData : MutableList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        for(i in 0..5){
            recommendCardData.add(Recommend(R.drawable.card,"$i","Type$i","Address$i","$i"))
        }
        for(i in 0..5){
            commentData.add("$i")
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_recommend, container, false)
        recyclerRecommendCardList = view.findViewById(R.id.recyclerRecommendCardList)
        recyclerCommentList = view.findViewById(R.id.recyclerCommentList)
        val recommendAdapter = RecommendCardRecyclerViewAdapter(requireContext(), recommendCardData)
        val cardItemTouchCallback = CardItemTouchHelperCallback(recommendAdapter, recommendCardData)
        cardItemTouchCallback.setOnSwipedListener(object : OnSwipeListener<Recommend>{
            override fun onSwiped(p0: RecyclerView.ViewHolder?, p1: Recommend?, p2: Int) {
            }
            override fun onSwipedClear() {
            }
            override fun onSwiping(p0: RecyclerView.ViewHolder?, p1: Float, p2: Int) {
            }
        })
        val itemTouchHelper = ItemTouchHelper(cardItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerRecommendCardList)
        recyclerRecommendCardList.apply {
            adapter = recommendAdapter
            layoutManager = CardLayoutManager(this, itemTouchHelper)
        }
        recyclerCommentList.apply {
            adapter = CommentRecyclerViewAdapter(context,commentData)
            layoutManager = LinearLayoutManager(context)
        }
        return view;
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}