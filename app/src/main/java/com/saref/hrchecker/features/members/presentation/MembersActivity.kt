package com.saref.hrchecker.features.members.presentation

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.saref.hrchecker.R
import com.saref.hrchecker.features.memberInfo.presentation.MemberInfoActivity
import com.saref.hrchecker.features.members.data.MembersRepositoryImpl
import com.saref.hrchecker.features.members.data.network.MemberPostResponse
import com.saref.hrchecker.features.members.data.network.dto.MemberPostDto
import com.saref.hrchecker.features.members.domain.Member
import com.saref.hrchecker.features.statistic.presentation.StatisticActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_members.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
            intent.putExtra(EVENT_ID_EXTRA, eventId)
            intent.putExtra(EVENT_TITLE_EXTRA, eventTitle)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = intent.getStringExtra(EVENT_TITLE_EXTRA)
        eventId = intent.getIntExtra(EVENT_ID_EXTRA, -1)
        initiateRecycleView()
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
                        adapter.updateMembers(memberList)
                    }

    }

    private fun getPresentMembers()
    {
        membersLoader =
                MembersRepositoryImpl().getPresentMembers(eventId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        if (result.isNotEmpty())
                        {
                            sendMembers(result)
                        }
                    }
    }

    private fun sendMembers(membersList: List<MemberPostDto>)
    {
        val call: Call<MemberPostResponse> =
            MembersRepositoryImpl().sendMembersToServer(eventId, membersList)
        call.enqueue(object : Callback<MemberPostResponse>
        {
            override fun onResponse(
                call: Call<MemberPostResponse>, response: Response<MemberPostResponse>
            )
            {
                Toast.makeText(
                    this@MembersActivity,
                    getString(R.string.postRequestSuccessfulToast),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<MemberPostResponse>, t: Throwable)
            {
                Toast.makeText(
                    this@MembersActivity, getString(R.string.postRequestErrorToast),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initiateRecycleView()
    {
        membersListView.layoutManager = LinearLayoutManager(this)
        membersListView.addItemDecoration(
            DividerItemDecoration(
                membersListView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        adapter = MemberListAdapter(object :
            MemberListAdapter.ItemClickListener
        {
            override fun onItemClick(member: Member)
            {
                MemberInfoActivity.startActivity(this@MembersActivity, member)
            }
        }, object : MemberListAdapter.CheckBoxClickListener
        {
            override fun onCheckBoxClick(member: Member)
            {
                membersLoader = Observable.just(MembersRepositoryImpl())
                    .subscribeOn(Schedulers.io())
                    .subscribe { repository -> repository.updateMember(member) }
            }

        })
        membersListView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.members_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String): Boolean
            {
                adapter.getFilter().filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean
            {
                adapter.getFilter().filter(query)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.itemId == android.R.id.home)
        {
            finish()
        }
        if (item.itemId == R.id.statisticMenuButton)
        {
            StatisticActivity.startActivity(
                this,
                memberList.size,
                memberList.count { member -> member.presentStatus },
                title.toString()
            )
        }
        if (item.itemId == R.id.sendMembersButton)
        {
            getPresentMembers()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop()
    {
        super.onStop()
        membersLoader.dispose()
    }
}
