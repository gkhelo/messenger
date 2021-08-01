package ge.gkhelashvili.messenger.chat

import com.google.firebase.storage.StorageReference

interface IChatPresenter {

    fun getAvatarReference(avatar: String): StorageReference
}