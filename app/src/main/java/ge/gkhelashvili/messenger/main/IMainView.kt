package ge.gkhelashvili.messenger.main

import ge.gkhelashvili.messenger.model.User

interface IMainView {
    abstract fun setProfileInfo(user: User)
    fun onSignedOut()
}