package ge.gkhelashvili.messenger.main

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import ge.gkhelashvili.messenger.getTimeDifference
import ge.gkhelashvili.messenger.model.Conversation
import ge.gkhelashvili.messenger.model.Message
import ge.gkhelashvili.messenger.model.User
import java.io.ByteArrayOutputStream

class MainInteractor(val presenter: IMainPresenter) {

    private val database = Firebase.database
    private val auth = Firebase.auth
    private val messages = database.getReference("messages")
    private val users = database.getReference("users")
    private val avatars = Firebase.storage.reference.child("avatars")
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

        val pictureRef = Firebase.storage.getReferenceFromUrl(
            "gs://messenger-9de03.appspot.com/avatars/${auth.currentUser!!.uid}.jpg")

        presenter.setProfileImage(pictureRef)
    }

    fun updateUserInfo(userInfo: User, oldUsername: String) {
        users
            .orderByChild("username")
            .equalTo(userInfo.username)
            .get()
            .addOnSuccessListener {
                if (it.children.count() > 1){
                    presenter.onUnsuccessfulInfoFetch()
                }else if(it.children.count() > 0 && oldUsername != userInfo.username){
                    presenter.onUnsuccessfulInfoFetch()
                }else{
                    users.child(auth.currentUser!!.uid).setValue(userInfo)
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
                if (message != null) {
                    if (message.toUser.equals(auth.currentUser!!.uid)){
                        updateConv(message, message.fromUser)
                    } else if(message.fromUser.equals(auth.currentUser!!.uid)) {
                        updateConv(message, message.toUser)
                    }
                }
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

    private fun updateConv(message: Message, userId: String?) {
        if (userId != null) {
            users.child(userId).get().addOnSuccessListener {
                val user = it.getValue<User>()
                if (user != null) {
                    user.id = it.key
                    val conv = Conversation(user,
                        message.time?.getTimeDifference(), message.text, user.avatar)
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

    fun setConversationsInfo(filter: String) {
        val newConversations = mutableListOf<Conversation>()
        for (conversation in conversations){
            if (conversation.toUser?.username?.startsWith(filter, ignoreCase = true) == true){
                newConversations.add(conversation)
            }
        }
        presenter.onConversationsInfoFetched(newConversations.toList(), -2)
    }

    fun uploadImage(bitmap: Bitmap?) {
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val pictureRef = avatars.child("${auth.currentUser!!.uid}.jpg")

        val uploadTask = pictureRef.putBytes(data)
        uploadTask.addOnFailureListener {
            presenter.onUnsuccessfulInfoFetch()
        }.addOnSuccessListener { taskSnapshot ->
            Log.d("SUCC", "SUCC")
        }

    }

    fun getAvatarReference(id: String): StorageReference {
        return Firebase.storage.getReferenceFromUrl(
            "gs://messenger-9de03.appspot.com/avatars/${id}.jpg")
    }


    companion object {
        const val TAG = "Main Interactor"
    }
}