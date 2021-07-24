package ge.gkhelashvili.messenger.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val id: String? = null,
    val username: String? = null,
    val password: String? = null,
    val profession: String? = null,
    val avatarUrl: String? = null
)
