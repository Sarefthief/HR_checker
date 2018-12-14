package com.saref.hrchecker.features.memberInfo.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.saref.hrchecker.R
import com.saref.hrchecker.features.members.domain.Member
import com.saref.hrchecker.utils.setTextHTML
import kotlinx.android.synthetic.main.activity_member_info.*

class MemberInfoActivity : AppCompatActivity()
{
    companion object
    {
        private const val MEMBER_EXTRA = "MEMBER_EXTRA"

        fun startActivity(context: Context, member: Member)
        {
            val intent = Intent(context, MemberInfoActivity::class.java)
            intent.putExtra(MEMBER_EXTRA, member)
            context.startActivity(intent)
        }
    }

    private lateinit var member: Member

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_info)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        member = intent.getSerializableExtra(MEMBER_EXTRA) as Member
        title = "${member.lastName} ${member.firstName}"
        showMember()
    }

    private fun showMember()
    {
        patronymicText.text = member.patronymic
        phoneText.text = member.phone
        emailText.text = member.email
        cityText.text = member.city
        companyText.text = member.company
        positionText.text = member.position
        if (member.addition.isEmpty())
        {
            additionTextView.visibility = View.GONE
        }
        else
        {
            additionText.text = member.addition.setTextHTML()
        }
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
