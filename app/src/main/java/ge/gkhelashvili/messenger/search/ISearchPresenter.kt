package ge.gkhelashvili.messenger.search

import ge.gkhelashvili.messenger.model.User

interface ISearchPresenter {

    fun getAllUsers()

    fun onUsersFetched(users: List<User>?)
}