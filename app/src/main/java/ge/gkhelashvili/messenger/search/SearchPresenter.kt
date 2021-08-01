package ge.gkhelashvili.messenger.search

import com.google.firebase.storage.StorageReference
import ge.gkhelashvili.messenger.model.User

class SearchPresenter(private val view: ISearchView) : ISearchPresenter {

    private val interactor = SearchInteractor(this)

    override fun fetchAllUsers() {
        interactor.fetchAllUsers()
    }

    override fun fetchUsers(name: String) {
        interactor.fetchUsers(name)
    }

    override fun getAvatarReference(avatar: String): StorageReference {
        return interactor.getAvatarReference(avatar)
    }

    override fun onUsersFetched(users: List<User>?) {
        view.showUsers(users)
    }

}