package ge.gkhelashvili.messenger.chat

import com.google.firebase.database.ChildEventListener
import com.google.firebase.storage.StorageReference
import ge.gkhelashvili.messenger.model.Message

class ChatPresenter(private val view: IChatView) : IChatPresenter {

    private val interactor = ChatInteractor(this)

    override fun getCurrentUserId(): String? {
        return interactor.getCurrentUserId()
    }

    override fun fetchMessages(user1: String, user2: String) {
        interactor.fetchMessages(user1, user2)
    }

    override fun sendMessage(message: Message) {
        interactor.sendMessage(message)
    }

    override fun registerMessagesListener(user1: String, user2: String): ChildEventListener {
        return interactor.registerMessagesListener(user1, user2)
    }

    override fun removeMessagesListener(listener: ChildEventListener) {
        interactor.removeMessagesListener(listener)
    }

    override fun getAvatarReference(avatar: String): StorageReference {
        return interactor.getAvatarReference(avatar)
    }

    override fun onMessagesFetched(messages: MutableList<Message>?) {
        view.showMessages(messages)
    }

    override fun onMessageAdded(message: Message) {
        view.addMessage(message)
    }
}