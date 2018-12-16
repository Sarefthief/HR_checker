package com.saref.hrchecker.features.events.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.saref.hrchecker.R
import com.saref.hrchecker.features.events.domain.entity.Event
import com.saref.hrchecker.features.members.presentation.MembersActivity
import kotlinx.android.synthetic.main.activity_events.*

class EventsActivity : AppCompatActivity(), EventsContract.View
{
    private lateinit var adapter: EventListAdapter
    private lateinit var presenter: EventsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        eventsListView.layoutManager = LinearLayoutManager(this)

        presenter = EventsPresenter()
        presenter.attachView(this)
    }

    override fun initiateAdapter()
    {
        adapter = EventListAdapter(object :
            EventListAdapter.ClickListener
        {
            override fun onItemClick(eventId: Int, eventTitle: String)
            {
                MembersActivity.startActivity(this@EventsActivity, eventId, eventTitle)
            }
        })
        eventsListView.adapter = adapter
    }

    override fun updateAdapter(eventsList: List<Event>) = adapter.updateEvents(eventsList)

    override fun showProgressBar()
    {
        eventsListProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar()
    {
        eventsListProgressBar.visibility = View.GONE
    }

    override fun onStop()
    {
        super.onStop()
        presenter.detachView()
    }
}
