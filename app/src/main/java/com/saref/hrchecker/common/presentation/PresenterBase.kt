package com.saref.hrchecker.common.presentation

abstract class PresenterBase<T : MvpView> : MvpPresenter<T>
{
    var view: T? = null
        private set


    override fun attachView(mvpView: T)
    {
        view = mvpView
    }

    override fun detachView()
    {
        view = null
    }
}