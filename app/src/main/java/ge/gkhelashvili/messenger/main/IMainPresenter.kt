package ge.gkhelashvili.messenger.main

import ge.gkhelashvili.messenger.model.Conversation
import ge.gkhelashvili.messenger.model.User

interface IMainPresenter {
    fun isUserSignedIn(): Boolean
    fun getProfileInfo()
    fun onProfileInfoFetched(user: User)
    fun onConversationsInfoFetched(conversations: List<Conversation>)
    abstract fun updateUserInfo(userInfo: User)
    fun signOut()
    fun onSignedOut()
    fun onUnsuccessfulInfoFetch()
    fun getConversationsInfo()
}