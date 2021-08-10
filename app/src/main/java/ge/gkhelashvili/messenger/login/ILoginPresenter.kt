package ge.gkhelashvili.messenger.login

interface ILoginPresenter {
    abstract fun onCredentialsValidated(success: Boolean)
}