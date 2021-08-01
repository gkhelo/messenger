package ge.gkhelashvili.messenger.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class Message(
    val id: String? = null,
    val fromUser: String? = null, // user id
    val toUser: String? = null, // user id
    val time: Date? = null,
    val text: String? = null
)