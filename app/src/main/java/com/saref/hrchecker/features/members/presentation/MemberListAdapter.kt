package com.saref.hrchecker.features.members.presentation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import com.saref.hrchecker.R
import com.saref.hrchecker.features.members.domain.entity.Member
import com.saref.hrchecker.utils.Constants
import kotlinx.android.synthetic.main.item_member.view.*
import java.text.SimpleDateFormat
import java.util.*

class MemberListAdapter(
    private val itemClickListener: ItemClickListener,
    private val checkBoxClickListener: CheckBoxClickListener
) : RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder>()
{
    private var memberList: List<Member> = arrayListOf()
    private var filteredMemberList: List<Member> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, pi: Int): MemberListViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
        return MemberListViewHolder(view)
    }

    override fun getItemCount(): Int = filteredMemberList.size

    override fun onBindViewHolder(viewHolder: MemberListViewHolder, position: Int)
    {
        viewHolder.bind(filteredMemberList[position], itemClickListener)
        viewHolder.itemView.checkBox.isChecked = filteredMemberList[position].presentStatus
        viewHolder.itemView.checkBox.setOnCheckedChangeListener { _, isChecked ->
            filteredMemberList[position].presentStatus = isChecked
            if (isChecked)
            {
                filteredMemberList[position].visitedDate = SimpleDateFormat(
                    Constants.VISITED_DATE_FORMAT,
                    Locale.getDefault()
                ).format(Date())
            }
            else
            {
                filteredMemberList[position].visitedDate = ""
            }

            checkBoxClickListener.onCheckBoxClick(filteredMemberList[position])
        }
    }

    fun updateMembers(memberList: List<Member>)
    {
        this.memberList = memberList
        this.filteredMemberList = memberList
        notifyDataSetChanged()
    }

    fun getFilter(): Filter
    {
        return object : Filter()
        {
            override fun performFiltering(charSequence: CharSequence): FilterResults
            {
                val charString = charSequence.toString()
                if (charString.isEmpty())
                {
                    filteredMemberList = memberList
                }
                else
                {
                    val filteredList: MutableList<Member> = arrayListOf()
                    for (row in memberList)
                    {

                        if (row.lastName.toLowerCase().contains(charString.toLowerCase()) ||
                            row.firstName.toLowerCase().contains(charString.toLowerCase())
                        )
                        {
                            filteredList.add(row)
                        }
                    }

                    filteredMemberList = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredMemberList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults)
            {
                if (filterResults.values is List<*>)
                {
                    filteredMemberList =
                            (filterResults.values as List<*>).filterIsInstance<Member>()
                    notifyDataSetChanged()
                }
            }
        }
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
            itemListener: ItemClickListener
        )
        {
            val name = "${member.lastName} ${member.firstName}"
            view.memberName.text = name

            view.setOnClickListener { itemListener.onItemClick(member) }
        }
    }
}