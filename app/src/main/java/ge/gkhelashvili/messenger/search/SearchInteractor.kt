package ge.gkhelashvili.messenger.search

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import ge.gkhelashvili.messenger.model.User

class SearchInteractor(private val presenter: ISearchPresenter) {

    private val users = Firebase.database.getReference("users")
    private val avatars = Firebase.storage.reference.child("avatars")

    fun getAllUsers() {
        users.get()
            .addOnSuccessListener {
                Log.i(TAG, "Successfully fetched ${it.childrenCount} users")

                val users = mutableListOf<User>()
                it.children.forEach { dataSnapshot ->
                    users.add(dataSnapshot.getValue(User::class.java) as User)
                }

                presenter.onUsersFetched(users)
            }
            .addOnFailureListener {
                Log.e(TAG, "Error occurred while trying to fetch users", it)
                presenter.onUsersFetched(null)
            }
    }

    fun getAvatarReference(avatar: String): StorageReference {
        return avatars.child(avatar)
    }

    companion object {
        const val TAG = "Search Interactor"
    }
}