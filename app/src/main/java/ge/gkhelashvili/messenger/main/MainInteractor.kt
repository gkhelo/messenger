package ge.gkhelashvili.messenger.main

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.gkhelashvili.messenger.model.User

class MainInteractor(val presenter: IMainPresenter) {

    private val database = Firebase.database
    private val auth = Firebase.auth
    private val messages = database.getReference("messages")
    private val users = database.getReference("users")

    fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }

    fun fetchProfileInfo() {
        users.child(auth.currentUser!!.uid).get().addOnSuccessListener {
            presenter.onProfileInfoFetched(
                User(profession = it.child("profession").getValue<String>(),
                     username = it.child("username").getValue<String>())
            )
        }

    }

    fun fetchConversationsInfo() {
        TODO("Not yet implemented")
    }

    fun updateUserInfo(userInfo: User) {
        users.child(auth.currentUser!!.uid).setValue(userInfo)
    }

    fun signOut() {
        auth.signOut()
        presenter.onSignedOut()
    }
}