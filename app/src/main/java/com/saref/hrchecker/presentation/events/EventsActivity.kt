package com.saref.hrchecker.presentation.events

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
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
        initiateRecycleView()
        getEventsFromDatabase()
    }

    private fun getEventsFromDatabase()
    {
        eventsListProgressBar.visibility = View.VISIBLE
        eventsLoader =
                EventsRepositoryImpl().getEventsFromDatabase()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        if (result.isNotEmpty())
                        {
                            eventList = result
                            updateRecycleView()
                            eventsListProgressBar.visibility = View.GONE
                        }
                        getEventsFromServer()
                    }
    }

    private fun getEventsFromServer()
    {
        eventsLoader =
                EventsRepositoryImpl().getEventsFromServer()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        eventList = result
                        updateRecycleView()
                        eventsListProgressBar.visibility = View.GONE
                    }
    }


    private fun initiateRecycleView()
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

    private fun updateRecycleView() = adapter.updateEvents(eventList)

    override fun onStop()
    {
        super.onStop()
        eventsLoader.dispose()
    }
}
