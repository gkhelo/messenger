package ge.gkhelashvili.messenger.register

import ge.gkhelashvili.messenger.model.User

class RegisterPresenter(private val view: IRegisterView) : IRegisterPresenter {

    private val interactor = RegisterInteractor(this)

    override fun validateUsername(username: String) {
        interactor.validateUsername(username)
    }

    override fun registerUser(username: String, password: String, profession: String) {
        interactor.registerUser(username, password, profession)
    }

    override fun onUsernameValidated(username: String?, isValid: Boolean) {
        view.onUsernameValidated(username, isValid)
    }

    override fun onUserRegistered(user: User?) {
        view.onUserRegistered(user)
    }


}