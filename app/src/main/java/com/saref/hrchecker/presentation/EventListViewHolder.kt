package com.saref.hrchecker.presentation

import android.support.v7.widget.RecyclerView
import android.view.View
import com.saref.hrchecker.data.EventDto
import kotlinx.android.synthetic.main.item_event.view.*


class EventListViewHolder (val view: View) : RecyclerView.ViewHolder(view)
{
    fun bind(eventDto: EventDto, listener: EventListAdapter.ClickListener) {

        view.eventTitle.text = eventDto.title
        view.eventStartDate.text = eventDto.date.start

        view.setOnClickListener{ listener.onItemClick() }
    }
}