package com.saref.hrchecker.features.events.presentation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saref.hrchecker.R
import com.saref.hrchecker.features.events.domain.entity.Event
import com.saref.hrchecker.utils.Constants
import kotlinx.android.synthetic.main.item_event.view.*
import java.text.SimpleDateFormat
import java.util.*

class EventListAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<EventListAdapter.EventListViewHolder>()
{
    private var eventList: List<Event> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, pi: Int): EventListViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventListViewHolder(view)
    }

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(viewHolder: EventListViewHolder, position: Int) =
        viewHolder.bind(eventList[position], clickListener)

    fun updateEvents(eventList: List<Event>)
    {
        this.eventList = eventList
        notifyDataSetChanged()
    }

    interface ClickListener
    {
        fun onItemClick(eventId: Int, eventTitle: String)
    }

    class EventListViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        fun bind(event: Event, listener: ClickListener)
        {
            view.eventTitle.text = event.title
            view.eventStartDate.text =
                    SimpleDateFormat(Constants.VISIBLE_DATE_FORMAT, Locale.getDefault()).format(
                        event.startDate
                    )
            view.setOnClickListener { listener.onItemClick(event.id, event.title) }
        }
    }
}