package ge.gkhelashvili.messenger.register

import android.graphics.Bitmap
import ge.gkhelashvili.messenger.model.User

class RegisterPresenter(private var view: IRegisterView?) : IRegisterPresenter {

    private val interactor = RegisterInteractor(this)

    override fun validateUsername(username: String) {
        interactor.validateUsername(username)
    }

    override fun registerUser(
        username: String,
        password: String,
        profession: String,
        bitmap: Bitmap
    ) {
        interactor.registerUser(username, password, profession, bitmap)
    }

    override fun onUsernameValidated(username: String?, isValid: Boolean) {
        view?.onUsernameValidated(username, isValid)
    }

    override fun onUserRegistered(user: User?, errorMessage: String?) {
        view?.onUserRegistered(user, errorMessage)
    }

    override fun detachView() {
        view = null
    }
}