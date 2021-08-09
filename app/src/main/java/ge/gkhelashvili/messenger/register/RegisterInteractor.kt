package ge.gkhelashvili.messenger.register

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.gkhelashvili.messenger.fakeMail
import ge.gkhelashvili.messenger.model.User

class RegisterInteractor(private val presenter: IRegisterPresenter) {

    private val auth = Firebase.auth
    private val users = Firebase.database.getReference("users")

    fun validateUsername(username: String) {
        Log.i(TAG, "Validating username: $username")

        users
            .orderByChild("username")
            .equalTo(username)
            .get()
            .addOnSuccessListener {
                Log.i(TAG, "Successfully validated username: $username")
                presenter.onUsernameValidated(username, it.children.count() == 0)
            }
            .addOnFailureListener {
                Log.e(TAG, "Error occurred while trying to validate username", it)
                presenter.onUsernameValidated(null, false)
            }
    }

    fun registerUser(username: String, password: String, profession: String) {
        Log.i(TAG, "Registering user: username[$username], profession[$profession]")

        auth.createUserWithEmailAndPassword(username.fakeMail(), password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i(TAG, "Successfully registered user: username[$username], profession[$profession]")
                    addUser(username, profession)
                } else {
                    Log.e(TAG, "Error occurred while trying to register user", it.exception)
                    presenter.onUserRegistered(null)
                }
            }
    }

    private fun addUser(username: String, profession: String) {
        val user = User(username = username, profession = profession)
        users.child(auth.currentUser?.uid!!).setValue(user)

        presenter.onUserRegistered(user)
    }

    companion object {
        const val TAG = "Register Interactor"
    }
}