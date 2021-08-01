package ge.gkhelashvili.messenger.chat

import com.google.firebase.storage.StorageReference
import ge.gkhelashvili.messenger.model.Message

class ChatPresenter(private val view: IChatView) : IChatPresenter {

    private val interactor = ChatInteractor(this)

    override fun fetchMessages() {
        interactor.fetchMessages()
    }

    override fun getAvatarReference(avatar: String): StorageReference {
        return interactor.getAvatarReference(avatar)
    }

    override fun onMessagesFetched(messages: MutableList<Message>?) {
        view.showMessages(messages)
    }
}