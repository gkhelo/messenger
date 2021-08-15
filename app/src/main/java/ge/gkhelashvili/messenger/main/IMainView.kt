package ge.gkhelashvili.messenger.main

import com.google.firebase.storage.StorageReference
import ge.gkhelashvili.messenger.model.Conversation
import ge.gkhelashvili.messenger.model.User

interface IMainView {
    abstract fun setProfileInfo(user: User)
    fun onSignedOut()
    fun showInfoFetchError()
    fun showConversations(conversations: List<Conversation>, index: Int)
    abstract fun setProfileImage(pictureRef: StorageReference)
}