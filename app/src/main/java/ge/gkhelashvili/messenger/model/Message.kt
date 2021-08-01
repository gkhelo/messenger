package ge.gkhelashvili.messenger.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Message(
    val id: String? = null,
    val fromUser: String? = null, // user id
    val toUser: String? = null, // user id
    val key: String? = null, // unique key for the message users
    val time: Date? = null,
    val text: String? = null
) : Comparable<Message> {

    override fun compareTo(other: Message): Int {
        return time!!.compareTo(other.time)
    }

}