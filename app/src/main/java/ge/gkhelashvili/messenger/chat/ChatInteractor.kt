package ge.gkhelashvili.messenger.chat

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import ge.gkhelashvili.messenger.model.Message
import java.util.*

class ChatInteractor(private val presenter: IChatPresenter) {

    private val messages = Firebase.database.getReference("messages")
    private val avatars = Firebase.storage.reference.child("avatars")

    fun fetchMessages() {
        val res = mutableListOf<Message>()

        res.add(
            Message(
                fromUser = "1234",
                toUser = "6789",
                time = Date(),
                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book"
            )
        )
        res.add(
            Message(
                fromUser = "6789",
                toUser = "1234",
                time = Date(),
                text = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable"
            )
        )
        res.add(
            Message(
                fromUser = "6789",
                toUser = "1234",
                time = Date(),
                text = "Mauris tincidunt nec velit sit amet viverra. Fusce a convallis metus. Nulla tristique orci non imperdiet tincidunt. Nulla quis ultricies diam, vitae suscipit libero"
            )
        )
        res.add(
            Message(
                fromUser = "1234",
                toUser = "6789",
                time = Date(),
                text = "Duis eu mi leo. Donec laoreet congue risus a dapibus"
            )
        )
        res.add(
            Message(
                fromUser = "1234",
                toUser = "6789",
                time = Date(),
                text = "Duis vulputate ligula sem, bibendum faucibus dolor aliquet vel"
            )
        )

        presenter.onMessagesFetched(res)
    }

    fun getAvatarReference(avatar: String): StorageReference {
        return avatars.child(avatar)
    }

    companion object {
        const val TAG = "Chat Interactor"
    }
}