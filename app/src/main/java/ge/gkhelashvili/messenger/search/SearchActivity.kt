package ge.gkhelashvili.messenger.search

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.model.User

class SearchActivity : AppCompatActivity(), ISearchView {

    private lateinit var presenter: SearchPresenter
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        Log.i(TAG, "Created activity")
        init()
    }

    private fun init() {
        presenter = SearchPresenter(this)
        usersAdapter = UsersAdapter(presenter)

        initUsers()
    }

    private fun initUsers() {
        findViewById<RecyclerView>(R.id.users_info).adapter = usersAdapter
        presenter.getAllUsers()
    }

    override fun showUsers(users: List<User>?) {
        if (users == null) {
            Toast.makeText(this, "Can't fetch users", Toast.LENGTH_SHORT).show()
            return
        }

        Log.i(TAG, "Showing fetched users")
        usersAdapter.users = users
        usersAdapter.notifyDataSetChanged()
    }

    companion object {
        const val TAG = "Search Activity"
    }
}