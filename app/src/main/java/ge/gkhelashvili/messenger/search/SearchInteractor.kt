package ge.gkhelashvili.messenger.search

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import ge.gkhelashvili.messenger.model.User

class SearchInteractor(private val presenter: ISearchPresenter) {

    private val users = Firebase.database.getReference("users")
    private val avatars = Firebase.storage.reference.child("avatars")

    fun fetchAllUsers() {
        users.get()
            .addOnSuccessListener { onSuccess(it) }
            .addOnFailureListener { onFailure(it) }
    }

    fun fetchUsers(name: String) {
        users
            .orderByChild("username")
            .equalTo(name)
            .get()
            .addOnSuccessListener { onSuccess(it) }
            .addOnFailureListener { onFailure(it) }
    }

    fun getAvatarReference(id: String): StorageReference {
        return Firebase.storage.getReferenceFromUrl(
            "gs://messenger-9de03.appspot.com/avatars/${id}.jpg")
    }

    private fun onSuccess(dataSnapshot: DataSnapshot) {
        Log.i(TAG, "Successfully fetched ${dataSnapshot.childrenCount} users")

        val users = mutableListOf<User>()
        dataSnapshot.children.forEach {
            val user = it.getValue(User::class.java) as User
            user.id = it.key

            users.add(user)
        }

        presenter.onUsersFetched(users)
    }

    private fun onFailure(ex: Exception) {
        Log.e(TAG, "Error occurred while trying to fetch users", ex)
        presenter.onUsersFetched(null)
    }

    companion object {
        const val TAG = "Search Interactor"
    }
}