package ge.gkhelashvili.messenger.search

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ge.gkhelashvili.messenger.R

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
        presenter = SearchPresenter()
        usersAdapter = UsersAdapter()

        initUsers()
    }

    private fun initUsers() {
        usersAdapter.users = presenter.getAllUsers()

        findViewById<RecyclerView>(R.id.users_info).adapter = usersAdapter
    }

    companion object {
        const val TAG = "Search Activity"
    }
}