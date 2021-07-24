package ge.gkhelashvili.messenger.search

import ge.gkhelashvili.messenger.model.User

interface ISearchView {

    fun showUsers(users: List<User>?)
}