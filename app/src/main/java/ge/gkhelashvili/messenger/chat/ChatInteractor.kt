package ge.gkhelashvili.messenger.chat

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class ChatInteractor {

    private val avatars = Firebase.storage.reference.child("avatars")

    fun getAvatarReference(avatar: String): StorageReference {
        return avatars.child(avatar)
    }
}