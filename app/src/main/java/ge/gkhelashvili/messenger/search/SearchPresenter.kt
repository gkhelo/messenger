package ge.gkhelashvili.messenger.search

import com.google.firebase.storage.StorageReference
import ge.gkhelashvili.messenger.model.User

class SearchPresenter(private var view: ISearchView?) : ISearchPresenter {

    private val interactor = SearchInteractor(this)

    override fun fetchAllUsers() {
        interactor.fetchAllUsers()
    }

    override fun fetchUsers(name: String) {
        interactor.fetchUsers(name)
    }

    override fun getAvatarReference(id: String): StorageReference {
        return interactor.getAvatarReference(id)
    }

    override fun onUsersFetched(users: List<User>?) {
        view?.showUsers(users)
    }

    override fun detachView() {
        view = null
    }
}