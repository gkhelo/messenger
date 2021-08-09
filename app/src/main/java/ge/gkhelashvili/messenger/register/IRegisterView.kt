package ge.gkhelashvili.messenger.register

import ge.gkhelashvili.messenger.model.User

interface IRegisterView {

    fun onUsernameValidated(username: String?, isValid: Boolean)

    fun onUserRegistered(user: User?)
}