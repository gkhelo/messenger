package ge.gkhelashvili.messenger.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.main.MainActivity
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

        initToolbar()
        initUsers()
    }

    private fun initToolbar() {
        initBackButton()
        initSearch()
    }

    private fun initUsers() {
        findViewById<RecyclerView>(R.id.users_info).adapter = usersAdapter
        presenter.fetchAllUsers()
    }

    private fun initBackButton() {
        findViewById<MaterialToolbar>(R.id.search_toolbar).setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun initSearch() {
        val layout = findViewById<TextInputLayout>(R.id.search_layout)
        val search = layout.findViewById<TextInputEditText>(R.id.search_edit_text)

        // TODO: Use debounce
        search.addTextChangedListener {
            val name = it.toString()
            if (name.isEmpty()) {
                presenter.fetchAllUsers()
            } else if (name.length > 3) {
                presenter.fetchUsers(name)
            }
        }
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