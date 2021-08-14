package ge.gkhelashvili.messenger.login

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import ge.gkhelashvili.messenger.fakeMail

class LoginInteractor(val presenter: ILoginPresenter) {

    private val auth = Firebase.auth
    private val users = Firebase.database.getReference("users")


    fun validateCredentials(username: String, password: String) {
        val mail = username.fakeMail()
        auth.signInWithEmailAndPassword(mail, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    presenter.onCredentialsValidated(true)
                }
            }.addOnFailureListener {
                Log.w(TAG, "signInWithEmail:failure", it)
                presenter.onCredentialsValidated(false)
            }
    }

    companion object {
        const val TAG = "Login Interactor"
    }
}