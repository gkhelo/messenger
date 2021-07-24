package ge.gkhelashvili.messenger.search

import ge.gkhelashvili.messenger.model.User

class SearchPresenter : ISearchPresenter {

    private val interactor = SearchInteractor()

    override fun getAllUsers(): List<User> {
        return interactor.getAllUsers()
    }

}