package ge.gkhelashvili.messenger.chat

import com.google.firebase.database.ChildEventListener
import com.google.firebase.storage.StorageReference
import ge.gkhelashvili.messenger.model.Message

interface IChatPresenter {

    fun fetchMessages(user1: String, user2: String)

    fun sendMessage(message: Message)

    fun registerMessagesListener(user1: String, user2: String): ChildEventListener

    fun removeMessagesListener(listener: ChildEventListener)

    fun getAvatarReference(avatar: String): StorageReference

    fun onMessagesFetched(messages: MutableList<Message>?)

    fun onMessageAdded(message: Message)
}