package ge.gkhelashvili.messenger.main

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.gkhelashvili.messenger.getTimeDifference
import ge.gkhelashvili.messenger.messageKey
import ge.gkhelashvili.messenger.model.Conversation
import ge.gkhelashvili.messenger.model.Message
import ge.gkhelashvili.messenger.model.User
import ge.gkhelashvili.messenger.register.RegisterInteractor

class MainInteractor(val presenter: IMainPresenter) {

    private val database = Firebase.database
    private val auth = Firebase.auth
    private val messages = database.getReference("messages")
    private val users = database.getReference("users")
    private val conversations = mutableListOf<Conversation>()

    fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }

    fun fetchProfileInfo() {
        users.child(auth.currentUser!!.uid).get().addOnSuccessListener {
            presenter.onProfileInfoFetched(
                User(profession = it.child("profession").getValue<String>(),
                    username = it.child("username").getValue<String>()
                )
            )
        }.addOnFailureListener {
            presenter.onUnsuccessfulInfoFetch()
        }
    }

    fun fetchConversationsInfo() {
        users.get().addOnSuccessListener {
            val usersList = it.children.toList()
            for (i in usersList.indices) {
                val user = usersList[i].getValue<User>()
                user!!.id = usersList[i].key
                if (i == (usersList.size - 1)) {
                    fetchConversationWithUser(user, true)
                } else {
                    fetchConversationWithUser(user, false)
                }
            }
        }.addOnFailureListener {
            Log.d("USERS", it.localizedMessage)
            presenter.onUnsuccessfulInfoFetch()
        }
    }

    private fun fetchConversationWithUser(user: User, isLast: Boolean) {
        val currUserId = auth.currentUser!!.uid
        messages
            .orderByChild("key")
            .equalTo(messageKey(currUserId, user.id!!))
            .get()
            .addOnSuccessListener {
                val messageList = it.children.toList()
                if(messageList.isNotEmpty()){
                    var lastMessage = messageList[0].getValue<Message>()
                    for (messageSnapshot in messageList){
                        val message = messageSnapshot.getValue<Message>()
                        if (message != null) {
                            if (message > lastMessage!!){
                                lastMessage = message
                            }
                        }
                    }
                    val conv = Conversation(user,
                        lastMessage?.time?.getTimeDifference(), lastMessage?.text, user.avatar)
                    conversations.add(conv)
                    if(isLast){
                        presenter.onConversationsInfoFetched(conversations.toList())
                    }
                }
            }
            .addOnFailureListener {
                Log.d("MESSAGE", it.localizedMessage)
                presenter.onUnsuccessfulInfoFetch()
            }
    }

    fun updateUserInfo(userInfo: User) {
        users
            .orderByChild("username")
            .equalTo(userInfo.username)
            .get()
            .addOnSuccessListener {
                if(it.children.count() == 0){
                    users.child(auth.currentUser!!.uid).setValue(userInfo)
                }else{
                    presenter.onUnsuccessfulInfoFetch()
                }
            }
            .addOnFailureListener {
                presenter.onUnsuccessfulInfoFetch()
            }
    }

    fun signOut() {
        auth.signOut()
        presenter.onSignedOut()
    }

    companion object {
        const val TAG = "Main Interactor"
    }
}