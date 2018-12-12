package com.saref.hrchecker.presentation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.saref.hrchecker.R
import com.saref.hrchecker.data.EventDto

class EventListAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<EventListViewHolder>()
{
    private var eventList : List<EventDto> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, pi: Int): EventListViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventListViewHolder(view)
    }

    override fun getItemCount(): Int
    {
        return eventList.size
    }

    override fun onBindViewHolder(viewHolder: EventListViewHolder, position: Int)
    {
        viewHolder.bind(eventList[position], clickListener)
    }

    fun updateEvents(eventList: List<EventDto>) {
        this.eventList = eventList
        notifyDataSetChanged()
    }

    interface ClickListener
    {
        fun onItemClick()
    }
}