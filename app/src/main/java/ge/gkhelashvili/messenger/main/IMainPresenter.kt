package ge.gkhelashvili.messenger.main

import android.graphics.Bitmap
import ge.gkhelashvili.messenger.model.Conversation
import ge.gkhelashvili.messenger.model.User

interface IMainPresenter {
    fun isUserSignedIn(): Boolean
    fun getProfileInfo()
    fun onProfileInfoFetched(user: User)
    fun onConversationsInfoFetched(conversations: List<Conversation>, index: Int)
    abstract fun updateUserInfo(userInfo: User, oldUsername: String)
    fun signOut()
    fun onSignedOut()
    fun onUnsuccessfulInfoFetch()
    fun getConversationsInfo()
    fun setConversationsInfo(filter: String)
    abstract fun uploadImage(bitmap: Bitmap?)
}