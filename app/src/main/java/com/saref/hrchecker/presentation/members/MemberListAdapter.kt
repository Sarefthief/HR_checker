package com.saref.hrchecker.presentation.members

import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saref.hrchecker.R
import com.saref.hrchecker.domain.entity.Member
import kotlinx.android.synthetic.main.item_member.view.*

class MemberListAdapter(
    private val itemClickListener: ItemClickListener,
    private val checkBoxClickListener: CheckBoxClickListener
) : RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder>()
{
    private var memberList: List<Member> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, pi: Int): MemberListViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
        return MemberListViewHolder(view)
    }

    override fun getItemCount(): Int = memberList.size

    override fun onBindViewHolder(viewHolder: MemberListViewHolder, position: Int) =
        viewHolder.bind(memberList[position], itemClickListener, checkBoxClickListener)

    fun updateMembers(memberList: List<Member>)
    {
        this.memberList = memberList
        notifyDataSetChanged()
    }

    interface ItemClickListener
    {
        fun onItemClick(member: Member)
    }

    interface CheckBoxClickListener
    {
        fun onCheckBoxClick(member: Member)
    }

    class MemberListViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        fun bind(
            member: Member,
            itemListener: MemberListAdapter.ItemClickListener,
            checkBoxListener: MemberListAdapter.CheckBoxClickListener
        )
        {
            val name = "${member.lastName} ${member.firstName}"
            view.memberName.text = name
            view.checkBox.isChecked = member.presentStatus
            view.setOnClickListener { itemListener.onItemClick(member) }
            view.checkBox.setOnCheckedChangeListener{ _, isChecked ->
                member.presentStatus = isChecked
                checkBoxListener.onCheckBoxClick(member)
            }
        }
    }
}