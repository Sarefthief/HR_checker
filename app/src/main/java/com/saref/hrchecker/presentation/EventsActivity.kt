package com.saref.hrchecker.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.saref.hrchecker.R
import com.saref.hrchecker.data.DataApiService
import com.saref.hrchecker.data.EventDto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_events.*

class EventsActivity : AppCompatActivity()
{
    private lateinit var disposable: Disposable
    private lateinit var eventList: List<EventDto>
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
        disposable =
                DataApiService.dataApiService.getEvents()
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
            override fun onItemClick()
            {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        eventsListView.adapter  = adapter
        adapter.updateEvents(eventList)
    }

    override fun onStop()
    {
        super.onStop()
        disposable.dispose()
    }
}
