package ge.gkhelashvili.messenger.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Conversation(
    val toUser: User? = null,
    val lastMessagetime: String? = null,
    val lastMessage: String? = null,
    val toUserAvatar: String? = null
) : Comparable<Conversation> {

    override fun compareTo(other: Conversation): Int {
        return lastMessagetime!!.compareTo(other.lastMessagetime!!)
    }

}