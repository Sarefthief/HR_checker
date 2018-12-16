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
import com.saref.hrchecker.features.members.domain.entity.Member
import com.saref.hrchecker.features.statistic.presentation.StatisticActivity
import kotlinx.android.synthetic.main.activity_members.*

class MembersActivity : AppCompatActivity(), MembersContract.View
{
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

    private lateinit var adapter: MemberListAdapter
    private lateinit var presenter: MembersContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = intent.getStringExtra(EVENT_TITLE_EXTRA)

        presenter = MembersPresenter()
        (presenter as MembersPresenter).attachView(this, intent.getIntExtra(EVENT_ID_EXTRA, -1))
    }

    override fun initiateAdapter()
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
                presenter.updateMember(member)
            }
        })
        membersListView.adapter = adapter
    }

    override fun updateAdapter(membersList: List<Member>)
    {
        adapter.updateMembers(membersList)
    }

    override fun showToast(text: String)
    {
        Toast.makeText(
            this, text, Toast.LENGTH_SHORT
        ).show()
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
                adapter.getMemberListCount(),
                adapter.getPresentMembersCount(),
                title.toString()
            )
        }
        if (item.itemId == R.id.sendMembersButton)
        {
            presenter.sendMembers()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop()
    {
        super.onStop()
        presenter.detachView()
    }
}
