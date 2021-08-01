package ge.gkhelashvili.messenger.chat

import com.google.firebase.storage.StorageReference

class ChatPresenter : IChatPresenter {

    private val interactor = ChatInteractor()

    override fun getAvatarReference(avatar: String): StorageReference {
        return interactor.getAvatarReference(avatar)
    }
}