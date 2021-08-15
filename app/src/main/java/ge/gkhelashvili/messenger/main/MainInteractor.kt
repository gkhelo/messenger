package ge.gkhelashvili.messenger.main

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.gkhelashvili.messenger.getTimeDifference
import ge.gkhelashvili.messenger.model.Conversation
import ge.gkhelashvili.messenger.model.Message
import ge.gkhelashvili.messenger.model.User

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

    fun setConversationsListeners() {
        messages.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue<Message>()
                updateConv(message)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("MESSAGESLISTENER", error.message)
                presenter.onUnsuccessfulInfoFetch()
            }

        })
    }

    private fun updateConv(message: Message?) {
        if (message != null) {
            message.toUser?.let { toUser ->
                users.child(toUser).get().addOnSuccessListener {
                    val user = it.getValue<User>()
                    if (user != null) {
                        user.id = it.key
                        val conv = Conversation(user,
                            message.time?.getTimeDifference(), message.text, user.avatar
                        )
                        var index = -1
                        for (i in 0 until conversations.size) {
                            if (conversations[i].toUser?.id.equals(user.id)){
                                index = i
                                conversations.removeAt(i)
                                break
                            }
                        }
                        conversations.add(0, conv)
                        presenter.onConversationsInfoFetched(conversations.toList(), index)
                    }
                }
            }
        }
    }


    companion object {
        const val TAG = "Main Interactor"
    }
}