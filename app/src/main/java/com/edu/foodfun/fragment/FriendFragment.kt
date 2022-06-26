package com.edu.foodfun.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.foodfun.R
import com.edu.foodfun.adapter.FriendRecyclerViewAdapter
import com.edu.foodfun.adapter.StrangerRecyclerViewAdapter

class FriendFragment : Fragment() {
    private lateinit var recyclerFriendList: RecyclerView
    private lateinit var recyclerStrangerList: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_friend, container, false)
        recyclerFriendList = view.findViewById(R.id.recyclerFriendList)
        recyclerStrangerList = view.findViewById(R.id.recyclerStrangerList)
        recyclerFriendList.apply {
            adapter = FriendRecyclerViewAdapter(context, listOf("Nacke", "Jacky", "John", "Mary", "Jason", "Daniel", "Amy", "Sandy", "Joe"))
            layoutManager = LinearLayoutManager(context)
        }
        recyclerStrangerList.apply {
            adapter = StrangerRecyclerViewAdapter(context, listOf("隔壁老王", "謝和弦", "吳宗憲", "劉德華", "張學友", "王識賢", "楊丞琳"))
            layoutManager = LinearLayoutManager(context)
        }
        return view;
    }
}