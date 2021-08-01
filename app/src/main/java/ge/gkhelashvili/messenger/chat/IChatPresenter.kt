package ge.gkhelashvili.messenger.chat

import com.google.firebase.storage.StorageReference
import ge.gkhelashvili.messenger.model.Message

interface IChatPresenter {

    fun fetchMessages(user1: String, user2: String)

    fun getAvatarReference(avatar: String): StorageReference

    fun onMessagesFetched(messages: MutableList<Message>?)
}