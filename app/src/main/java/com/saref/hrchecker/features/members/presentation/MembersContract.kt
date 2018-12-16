package com.saref.hrchecker.features.members.presentation

import com.saref.hrchecker.common.presentation.MvpPresenter
import com.saref.hrchecker.common.presentation.MvpView
import com.saref.hrchecker.features.members.domain.entity.Member

interface MembersContract
{
    interface View : MvpView
    {
        fun initiateAdapter()

        fun updateAdapter(membersList: List<Member>)

        fun showToast(text: String)
    }

    interface Presenter: MvpPresenter<View>
    {
        fun updateMember(member: Member)

        fun sendMembers()
    }
}