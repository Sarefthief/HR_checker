package com.saref.hrchecker.common.presentation

interface MvpPresenter<V : MvpView>
{
    fun attachView(mvpView: V)

    fun detachView()
}