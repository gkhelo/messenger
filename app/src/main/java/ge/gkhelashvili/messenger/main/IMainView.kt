package ge.gkhelashvili.messenger.main

import ge.gkhelashvili.messenger.model.Conversation
import ge.gkhelashvili.messenger.model.User

interface IMainView {
    abstract fun setProfileInfo(user: User)
    fun onSignedOut()
    fun showInfoFetchError()
    fun showConversations(conversations: List<Conversation>)
}