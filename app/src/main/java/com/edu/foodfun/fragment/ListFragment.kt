package com.edu.foodfun.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.foodfun.R
import com.edu.foodfun.SimpleRest
import com.edu.foodfun.ListSwipe
import com.edu.foodfun.adapter.FavoriteListRecyclerViewAdapter
import com.edu.foodfun.adapter.WillEatListRecyclerViewAdapter
import com.edu.foodfun.enum.ListSwipeType
import com.google.android.gms.maps.model.LatLng

class ListFragment(private val willEatList: MutableList<SimpleRest>, private val favoriteList: MutableList<SimpleRest>) : Fragment()  {
    private lateinit var btnWillEat: Button
    private lateinit var btnFavorite: Button
    private lateinit var recyclerRestList: RecyclerView
    private val mapFragment = MapFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        for (i in 0..7) {
//            willEatList.add(SimpleRest("Tasty$i", "${i}mins"))
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        childFragmentManager.beginTransaction().replace(R.id.fragmentMap, mapFragment).addToBackStack(null).commit()
        btnWillEat = view.findViewById(R.id.btnWillEat)
        btnFavorite = view.findViewById(R.id.btnFavorite)
        recyclerRestList = view.findViewById(R.id.recyclerRestList)
        btnWillEat.isSelected = true
//        val nutc = LatLng(24.149704520502556, 120.6838030129633)


        val willEatListRecyclerViewAdapter = WillEatListRecyclerViewAdapter(view.context, willEatList,object : WillEatListRecyclerViewAdapter.SimpleRestObserver{
            override fun onFavorite(index: Int, simpleRest: SimpleRest) {
                favoriteList.add(simpleRest)
            }

            override fun onItemClick(index: Int, simpleRest: SimpleRest) {
                mapFragment.switchMarker(simpleRest.coordinate, simpleRest.restName)
                mapFragment.redirectToMarker()
            }
        })
        val favoriteListRecyclerViewAdapter = FavoriteListRecyclerViewAdapter(view.context, favoriteList)
        val swipeToDelete = object : ListSwipe(this,ListSwipeType.WILLEAT) {}
        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(recyclerRestList)

        recyclerRestList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = willEatListRecyclerViewAdapter
        }

        btnWillEat.setOnClickListener {
            btnWillEat.isSelected = true
            btnFavorite.isSelected = false
            swipeToDelete.setSwipeType(ListSwipeType.WILLEAT)
            recyclerRestList.apply {
                adapter = willEatListRecyclerViewAdapter
            }
        }
        btnFavorite.setOnClickListener {
            btnWillEat.isSelected = false
            btnFavorite.isSelected = true
//            val favoriteList = GlobalVar.getList()
            swipeToDelete.setSwipeType(ListSwipeType.FAVORITE)
            recyclerRestList.apply {
                adapter = favoriteListRecyclerViewAdapter
            }
        }
        return view
    }

}
