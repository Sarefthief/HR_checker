package com.saref.hrchecker.features.statistic.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.saref.hrchecker.R
import kotlinx.android.synthetic.main.activity_statistic.*

class StatisticActivity : AppCompatActivity()
{
    companion object
    {
        private const val MEMBERS_COUNT_EXTRA = "MEMBERS_COUNT_EXTRA"
        private const val CHECKED_MEMBERS_COUNT_EXTRA = "CHECKED_MEMBERS_COUNT_EXTRA"
        private const val EVENT_TITLE_EXTRA = "EVENT_TITLE_EXTRA"

        fun startActivity(context: Context, membersCount: Int, checkedMembersCount: Int, eventTitle: String)
        {
            val intent = Intent(context, StatisticActivity::class.java)
            intent.putExtra(MEMBERS_COUNT_EXTRA, membersCount)
            intent.putExtra(CHECKED_MEMBERS_COUNT_EXTRA, checkedMembersCount)
            intent.putExtra(EVENT_TITLE_EXTRA, eventTitle)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = intent.getStringExtra(EVENT_TITLE_EXTRA)
        showStatistics()
    }

    private fun showStatistics()
    {
        membersRegisteredText.text = intent.getIntExtra(MEMBERS_COUNT_EXTRA, -1).toString()
        membersCheckedText.text = intent.getIntExtra(CHECKED_MEMBERS_COUNT_EXTRA, -1).toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.itemId == android.R.id.home)
        {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}