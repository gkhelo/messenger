package ge.gkhelashvili.messenger.main

import ge.gkhelashvili.messenger.model.Conversation
import ge.gkhelashvili.messenger.model.User

class MainPresenter(var view: IMainView?): IMainPresenter {
    private val interactor = MainInteractor(this)

    override fun isUserSignedIn(): Boolean {
        return interactor.isUserSignedIn()
    }

    override fun getProfileInfo() {
        interactor.fetchProfileInfo()
    }

    override fun onProfileInfoFetched(user: User) {
        view?.setProfileInfo(user)
    }

    override fun onConversationsInfoFetched(conversations: List<Conversation>, index: Int) {
        view?.showConversations(conversations, index)
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

    override fun onUnsuccessfulInfoFetch() {
        view?.showInfoFetchError()
    }

    override fun getConversationsInfo() {
        interactor.setConversationsListeners()
    }

    fun detachView(){
        view = null
    }
}