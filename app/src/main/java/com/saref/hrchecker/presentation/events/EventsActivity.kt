package com.saref.hrchecker.presentation.events

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.saref.hrchecker.R
import com.saref.hrchecker.data.EventsRepositoryImpl
import com.saref.hrchecker.domain.entity.Event
import com.saref.hrchecker.presentation.members.MembersActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_events.*

class EventsActivity : AppCompatActivity()
{
    private lateinit var eventsLoader: Disposable
    private lateinit var eventList: List<Event>
    private lateinit var adapter: EventListAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        eventsListView.layoutManager = LinearLayoutManager(this)
        getEvents()
    }

    private fun getEvents()
    {
        eventsLoader =
                EventsRepositoryImpl().getEvents()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        eventList = result
                        initiateRecycleView()
                    }
    }

    private fun initiateRecycleView()
    {
        adapter = EventListAdapter(object :
            EventListAdapter.ClickListener
        {
            override fun onItemClick(eventId: Int, eventTitle: String)
            {
                MembersActivity.startActivity(this@EventsActivity, eventId, eventTitle )
            }

        })
        eventsListView.adapter = adapter
        adapter.updateEvents(eventList)
    }

    override fun onStop()
    {
        super.onStop()
        eventsLoader.dispose()
    }
}
