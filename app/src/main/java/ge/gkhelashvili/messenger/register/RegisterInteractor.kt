package ge.gkhelashvili.messenger.register

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ge.gkhelashvili.messenger.fakeMail
import ge.gkhelashvili.messenger.model.User
import java.io.ByteArrayOutputStream

class RegisterInteractor(private val presenter: IRegisterPresenter) {

    private val auth = Firebase.auth
    private val users = Firebase.database.getReference("users")
    private val avatars = Firebase.storage.reference.child("avatars")


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

    fun registerUser(username: String, password: String, profession: String, bitmap: Bitmap) {
        Log.i(TAG, "Registering user: username[$username], profession[$profession]")

        auth.createUserWithEmailAndPassword(username.fakeMail(), password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i(TAG, "Successfully registered user: username[$username], profession[$profession]")
                    addUser(username, profession)
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()
                    val pictureRef = avatars.child("${auth.currentUser!!.uid}.jpg")
                    pictureRef.putBytes(data)
                } else {
                    Log.e(TAG, "Error occurred while trying to register user", it.exception)
                    presenter.onUserRegistered(null, it.exception?.message)
                }
            }


    }

    private fun addUser(username: String, profession: String) {
        val user = User(username = username, profession = profession)
        users.child(auth.currentUser?.uid!!).setValue(user)

        presenter.onUserRegistered(user, null)
    }

    companion object {
        const val TAG = "Register Interactor"
    }
}