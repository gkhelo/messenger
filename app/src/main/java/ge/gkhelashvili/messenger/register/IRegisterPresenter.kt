package ge.gkhelashvili.messenger.register

import android.graphics.Bitmap
import ge.gkhelashvili.messenger.model.User

interface IRegisterPresenter {

    fun validateUsername(username: String)

    fun registerUser(username: String, password: String, profession: String, bitmap: Bitmap)

    fun onUsernameValidated(username: String?, isValid: Boolean)

    fun onUserRegistered(user: User?, errorMessage: String?)

    fun detachView()
}