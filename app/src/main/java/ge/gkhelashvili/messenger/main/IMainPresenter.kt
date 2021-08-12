package ge.gkhelashvili.messenger.main

import ge.gkhelashvili.messenger.model.User

interface IMainPresenter {
    fun isUserSignedIn(): Boolean
    fun getInfo(position: Int)
    fun onProfileInfoFetched(user: User)
    fun onConversationsInfoFetched()
    abstract fun updateUserInfo(userInfo: User)
    fun signOut()
    fun onSignedOut()
}