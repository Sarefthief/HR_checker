package com.saref.hrchecker.features.members.presentation

import com.saref.hrchecker.App
import com.saref.hrchecker.R
import com.saref.hrchecker.common.presentation.PresenterBase
import com.saref.hrchecker.features.members.data.network.entity.MemberPostDto
import com.saref.hrchecker.features.members.data.network.entity.MemberPostResponse
import com.saref.hrchecker.features.members.domain.entity.Member
import com.saref.hrchecker.features.members.domain.interactor.MembersInteractor
import com.saref.hrchecker.features.members.domain.interactor.MembersInteractorImpl
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MembersPresenter: PresenterBase<MembersContract.View>(), MembersContract.Presenter
{
    private lateinit var membersLoader: Disposable
    private var membersInteractor: MembersInteractor = MembersInteractorImpl()
    private var eventId: Int = -1

    fun attachView(mvpView: MembersContract.View, eventId: Int)
    {
        super.attachView(mvpView)
        this.eventId = eventId
        view?.initiateAdapter()
        getMembers()
    }

    override fun updateMember(member: Member)
    {
        membersLoader = Observable.just(membersInteractor)
            .subscribeOn(Schedulers.io())
            .subscribe { repository -> repository.updateMember(member) }
    }

    private fun getMembers()
    {
        membersLoader =
                membersInteractor.getMembers(eventId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        view?.updateAdapter(result)
                    }
    }

    override fun sendMembers()
    {
        membersLoader =
                membersInteractor.getPresentMembers(eventId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        if (result.isNotEmpty())
                        {
                            performSending(result)
                        }
                        else
                        {
                            view?.showToast(App.instance.getString(R.string.postRequestNothingToSend))
                        }
                    }
    }

    private fun performSending(membersList: List<MemberPostDto>)
    {
        val call: Call<MemberPostResponse> =
            membersInteractor.sendMembersToServer(eventId, membersList)
        call.enqueue(object : Callback<MemberPostResponse>
        {
            override fun onResponse(
                call: Call<MemberPostResponse>, response: Response<MemberPostResponse>
            )
            {
                view?.showToast(App.instance.getString(R.string.postRequestSuccessfulToast))
            }

            override fun onFailure(call: Call<MemberPostResponse>, t: Throwable)
            {
                view?.showToast(App.instance.getString(R.string.postRequestErrorToast))
            }
        })
    }

    override fun detachView()
    {
        super.detachView()
        membersLoader.dispose()
    }

}