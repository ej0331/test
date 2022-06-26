package com.edu.foodfun

import android.content.Context
import android.graphics.Canvas
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.edu.foodfun.enum.ListSwipeType

abstract class ListSwipe(fragment: Fragment, listSwipeType: ListSwipeType) : ItemTouchHelper.Callback() {
    private val fragment:Fragment=fragment
    private var listSwipeType : ListSwipeType = listSwipeType
    private var limitScrollX = 0
    private var currentScrollX = 0
    private var currentScrollXWhenInActive = 0
    private var initXWhenInActive = 0f
    private var firstInActive = false
    init {
        setSwipeType(listSwipeType)
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val flag=ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return  makeMovementFlags(0,flag)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("Not yet implemented")
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return Integer.MAX_VALUE.toFloat()
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return Integer.MAX_VALUE.toFloat()
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if(actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
            if(dX==0f){
                currentScrollX=viewHolder.itemView.scrollX
                firstInActive=true
            }
        }
        if(isCurrentlyActive){
            var scrollOffset=currentScrollX+(-dX).toInt()
            if(scrollOffset>limitScrollX){
                scrollOffset=limitScrollX
            }
            else if(scrollOffset<0){
                scrollOffset=0
            }
            viewHolder.itemView.scrollTo(scrollOffset,0)
        }
        else{
            if(firstInActive){
                firstInActive=false
                currentScrollXWhenInActive=viewHolder.itemView.scrollX
                initXWhenInActive=dX
            }
            if(viewHolder.itemView.scrollX<limitScrollX){
                viewHolder.itemView.scrollTo((currentScrollXWhenInActive* dX / initXWhenInActive).toInt(),0)
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if(viewHolder.itemView.scrollX>limitScrollX){
            viewHolder.itemView.scrollTo(limitScrollX,0)
        }
        else if(viewHolder.itemView.scrollX<0){
            viewHolder.itemView.scrollTo(0,0)
        }
    }

    fun setSwipeType(listSwipeType: ListSwipeType){
        this.listSwipeType = listSwipeType
        if(listSwipeType == ListSwipeType.WILLEAT){
            limitScrollX=dipToPx(180f, fragment.context)
        }
        else if(listSwipeType == ListSwipeType.FAVORITE){
            limitScrollX=dipToPx(90f, fragment.context)
        }
    }

    private fun dipToPx(dipValue: Float,context: Context?):Int{
        if(context == null)
            return -1
        return (dipValue*context.resources.displayMetrics.density).toInt()
    }
}