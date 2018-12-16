package com.saref.hrchecker.features.events.presentation

import com.saref.hrchecker.common.presentation.MvpPresenter
import com.saref.hrchecker.common.presentation.MvpView
import com.saref.hrchecker.features.events.domain.entity.Event

interface EventsContract
{
    interface View : MvpView
    {
        fun initiateAdapter()

        fun updateAdapter(eventsList: List<Event>)

        fun showProgressBar()

        fun hideProgressBar()
    }

    interface Presenter: MvpPresenter<View>

}