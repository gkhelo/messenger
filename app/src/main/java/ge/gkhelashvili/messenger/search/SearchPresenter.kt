package ge.gkhelashvili.messenger.search

import ge.gkhelashvili.messenger.model.User

class SearchPresenter(private val view: ISearchView) : ISearchPresenter {

    private val interactor = SearchInteractor(this)

    override fun getAllUsers() {
        interactor.getAllUsers()
    }

    override fun onUsersFetched(users: List<User>?) {
        view.showUsers(users)
    }

}