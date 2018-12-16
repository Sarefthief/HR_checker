package com.saref.hrchecker.features.events.presentation

import com.saref.hrchecker.common.presentation.PresenterBase
import com.saref.hrchecker.features.events.domain.interactor.EventsInteractor
import com.saref.hrchecker.features.events.domain.interactor.EventsInteractorImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EventsPresenter: PresenterBase<EventsContract.View>(), EventsContract.Presenter
{
    private lateinit var eventsLoader: Disposable
    private var eventsInteractor: EventsInteractor = EventsInteractorImpl()

    override fun attachView(mvpView: EventsContract.View)
    {
        super.attachView(mvpView)
        view?.initiateAdapter()
        getEventsFromDatabase()
    }

    override fun detachView()
    {
        super.detachView()
        eventsLoader.dispose()
    }

    private fun getEventsFromDatabase()
    {
        view?.showProgressBar()
        eventsLoader =
                eventsInteractor.getEventsFromDatabase()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        if (result.isNotEmpty())
                        {
                            view?.updateAdapter(result)
                            view?.hideProgressBar()
                        }
                        getEventsFromServer()
                    }
    }

    private fun getEventsFromServer()
    {
        eventsLoader =
                eventsInteractor.getEventsFromServer()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        view?.updateAdapter(result)
                        view?.hideProgressBar()
                    }
    }
}