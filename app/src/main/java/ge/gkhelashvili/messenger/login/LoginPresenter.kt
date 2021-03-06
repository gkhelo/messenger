package ge.gkhelashvili.messenger.login

class LoginPresenter(var view: ILoginView?): ILoginPresenter{
    private val interactor = LoginInteractor(this)

    fun validateCredentials(username: String, password: String) {
        if ((username.isEmpty()) or (password.isEmpty())){
            view?.showEmptyFieldError()
        }else {
            interactor.validateCredentials(username, password)
        }
    }

    override fun onCredentialsValidated(success: Boolean){
        if(success) {
            view?.showMainPage()
        }else{
            view?.showAuthorizationError()
        }
    }

    fun detachView(){
        view = null
    }
}