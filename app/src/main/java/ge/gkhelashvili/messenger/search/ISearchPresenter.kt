package ge.gkhelashvili.messenger.search

import com.google.firebase.storage.StorageReference
import ge.gkhelashvili.messenger.model.User

interface ISearchPresenter {

    fun fetchAllUsers()

    fun fetchUsers(name: String)

    fun getAvatarReference(avatar: String): StorageReference

    fun onUsersFetched(users: List<User>?)

    fun detachView()
}