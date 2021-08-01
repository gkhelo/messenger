package ge.gkhelashvili.messenger.chat

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import ge.gkhelashvili.messenger.messageKey
import ge.gkhelashvili.messenger.model.Message

class ChatInteractor(private val presenter: IChatPresenter) {

    private val messages = Firebase.database.getReference("messages")
    private val avatars = Firebase.storage.reference.child("avatars")

    fun fetchMessages(user1: String, user2: String) {
        messages
            .orderByChild("key")
            .equalTo(messageKey(user1, user2))
            .get()
            .addOnSuccessListener { onSuccess(it) }
            .addOnFailureListener { onFailure(it) }
    }

    fun registerMessagesListener(user1: String, user2: String): ChildEventListener {
        val listener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                presenter.onMessageAdded(snapshot.getValue(Message::class.java) as Message)
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        }

        messages
            .orderByChild("key")
            .equalTo(messageKey(user1, user2))
            .addChildEventListener(listener)

        return listener
    }

    fun removeMessagesListener(listener: ChildEventListener) {
        messages.removeEventListener(listener)
    }

    fun getAvatarReference(avatar: String): StorageReference {
        return avatars.child(avatar)
    }

    private fun onSuccess(dataSnapshot: DataSnapshot) {
        Log.i(TAG, "Successfully fetched ${dataSnapshot.childrenCount} messages")

        val messages = mutableListOf<Message>()
        dataSnapshot.children.forEach {
            messages.add(it.getValue(Message::class.java) as Message)
        }

        messages.sort()
        presenter.onMessagesFetched(messages)
    }

    private fun onFailure(ex: Exception) {
        Log.e(TAG, "Error occurred while trying to fetch messages", ex)
        presenter.onMessagesFetched(null)
    }

    companion object {
        const val TAG = "Chat Interactor"
    }
}