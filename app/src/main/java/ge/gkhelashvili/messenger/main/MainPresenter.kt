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

    override fun updateUserInfo(userInfo: User, oldUsername: String) {
        interactor.updateUserInfo(userInfo, oldUsername)
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

    override fun setConversationsInfo(filter: String) {
        interactor.setConversationsInfo(filter)
    }

    fun detachView(){
        view = null
    }
}