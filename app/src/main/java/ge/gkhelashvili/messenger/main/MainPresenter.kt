package ge.gkhelashvili.messenger.main

import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.model.User

class MainPresenter(var view: IMainView?): IMainPresenter {
    private val interactor = MainInteractor(this)

    override fun getInfo(position: Int) {
        //0 ->interactor.fetchConversationsInfo()
        when (position){

            1 -> interactor.fetchProfileInfo()
        }

    }

    override fun onProfileInfoFetched(user: User) {
        view?.setProfileInfo(user)
    }

    override fun onConversationsInfoFetched() {
        TODO("Not yet implemented")
    }

    override fun updateUserInfo(userInfo: User) {
        interactor.updateUserInfo(userInfo)
    }

    override fun signOut() {
        interactor.signOut()
    }

    override fun onSignedOut() {
        view?.onSignedOut()
    }


    fun detachView(){
        view = null
    }
}