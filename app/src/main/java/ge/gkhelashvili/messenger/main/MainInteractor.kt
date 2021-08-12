package ge.gkhelashvili.messenger.main

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.gkhelashvili.messenger.login.LoginInteractor
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
        users.child(auth.currentUser!!.uid).get().addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                presenter.onProfileInfoFetched(
                    User(profession = task.result?.child("profession")?.getValue<String>(),
                        username = task.result?.child("username")?.getValue<String>())
                )
            } else {
                Log.d(TAG, "INFO FETCH UNSUCCESSFUL")
                presenter.onUnsuccessfullProfileInfoFetch()
            }
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

    companion object {
        const val TAG = "Main Interactor"
    }
}