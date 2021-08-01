package ge.gkhelashvili.messenger.chat

import ge.gkhelashvili.messenger.model.Message

interface IChatView {

    fun showMessages(messages: MutableList<Message>?)

    fun addMessage(message: Message)
}