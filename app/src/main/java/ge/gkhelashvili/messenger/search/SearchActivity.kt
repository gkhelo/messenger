package ge.gkhelashvili.messenger.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.main.MainActivity
import ge.gkhelashvili.messenger.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SearchActivity : AppCompatActivity(), ISearchView, CoroutineScope {

    private lateinit var presenter: SearchPresenter
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        Log.i(TAG, "Created activity")
        init()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override val coroutineContext: CoroutineContext = Dispatchers.Main

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

        val watcher = object : TextWatcher {
            private var name = ""

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString().trim()
                if (text == name) {
                    return
                }

                name = text
                launch {
                    delay(300)  // debounce timeout

                    if (text != name)
                        return@launch

                    // Search users
                    if (name.isEmpty()) {
                        presenter.fetchAllUsers()
                    } else if (name.length > 3) {
                        presenter.fetchUsers(name)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        }

        search.addTextChangedListener(watcher)
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