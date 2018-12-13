package com.saref.hrchecker.presentation.members

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.saref.hrchecker.R
import com.saref.hrchecker.data.EventsRepositoryImpl
import com.saref.hrchecker.data.MembersRepositoryImpl
import com.saref.hrchecker.domain.entity.Event
import com.saref.hrchecker.domain.entity.Member
import com.saref.hrchecker.presentation.events.EventListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_events.*
import kotlinx.android.synthetic.main.activity_members.*

class MembersActivity : AppCompatActivity()
{
    private lateinit var membersLoader: Disposable
    private lateinit var memberList: List<Member>
    private lateinit var adapter: MemberListAdapter
    private var eventId: Int = -1

    companion object
    {
        private const val EVENT_ID_EXTRA = "EVENT_ID_EXTRA"
        private const val EVENT_TITLE_EXTRA = "EVENT_TITLE_EXTRA"

        fun startActivity(context: Context, eventId: Int, eventTitle: String)
        {
            val intent = Intent(context, MembersActivity::class.java)
            intent.putExtra(EVENT_ID_EXTRA,eventId)
            intent.putExtra(EVENT_TITLE_EXTRA, eventTitle)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)
        membersListView.layoutManager = LinearLayoutManager(this)
        title = intent.getStringExtra(EVENT_TITLE_EXTRA)
        eventId = intent.getIntExtra(EVENT_ID_EXTRA, -1)
        getMembers()
    }

    private fun getMembers()
    {
        membersLoader =
                MembersRepositoryImpl().getMembers(eventId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        memberList = result
                        initiateRecycleView()
                    }
    }

    private fun initiateRecycleView()
    {
        adapter = MemberListAdapter(object :
           MemberListAdapter.ClickListener
        {
            override fun onItemClick()
            {

            }

        })
        membersListView.adapter = adapter
        adapter.updateMembers(memberList)
    }

    override fun onStop()
    {
        super.onStop()
        membersLoader.dispose()
    }
}
