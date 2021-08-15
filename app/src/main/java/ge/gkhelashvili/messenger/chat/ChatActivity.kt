package ge.gkhelashvili.messenger.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.ChildEventListener
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.main.MainActivity
import ge.gkhelashvili.messenger.messageKey
import ge.gkhelashvili.messenger.model.Message
import ge.gkhelashvili.messenger.model.User
import ge.gkhelashvili.messenger.search.SearchActivity
import java.util.*

class ChatActivity : AppCompatActivity(), IChatView {

    private lateinit var user: User
    private lateinit var presenter: IChatPresenter
    private lateinit var messagesAdapter: MessagesAdapter

    private lateinit var listener: ChildEventListener

    private lateinit var currentUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        Log.i(TAG, "Created activity")
        init()
    }

    override fun onStart() {
        listener = presenter.registerMessagesListener(currentUserId, user.id!!)
        super.onStart()
    }

    override fun onStop() {
        presenter.removeMessagesListener(listener)
        super.onStop()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    private fun init() {
        val userSerializable = intent.getSerializableExtra("user")
        if (userSerializable == null || userSerializable !is User) {
            Toast.makeText(this, "Can't load user info", Toast.LENGTH_SHORT).show()
            return
        }

        user = userSerializable
        presenter = ChatPresenter(this)

        val id = presenter.getCurrentUserId()
        if (id == null) {
            Toast.makeText(this, "Can't load current user", Toast.LENGTH_SHORT).show()
            return
        }

        currentUserId = id
        messagesAdapter = MessagesAdapter(currentUserId)

        initToolbar()
        initMessages()
        initInput()
    }

    private fun initToolbar() {
        initBackButton()
        initUser()
    }

    private fun initMessages() {
        findViewById<RecyclerView>(R.id.messages).adapter = messagesAdapter
        presenter.fetchMessages(currentUserId, user.id!!)
    }

    private fun initInput() {
        val layout = findViewById<TextInputLayout>(R.id.chat_input_layout)
        val editText = findViewById<TextInputEditText>(R.id.chat_input_edittext)
        layout.setEndIconOnClickListener {
            val message = Message(
                fromUser = currentUserId,
                toUser = user.id,
                key = messageKey(currentUserId, user.id!!),
                time = Date(),
                text = editText.text.toString()
            )

            editText.setText("")
            presenter.sendMessage(message)
        }
    }

    private fun initBackButton() {
        findViewById<MaterialToolbar>(R.id.chat_toolbar).setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun initUser() {
        Log.i(TAG, "Showing user info")

        findViewById<TextView>(R.id.chat_user_name).text = user.username
        findViewById<TextView>(R.id.chat_user_profession).text = user.profession

        val avatar = findViewById<ImageView>(R.id.chat_user_avatar)
        if (user.id != null) {
            Glide
                .with(this)
                .load(presenter.getAvatarReference(user.id!!))
                .circleCrop()
                .into(avatar)
        } else {
            avatar.setImageResource(R.drawable.avatar_image_placeholder)
        }
    }

    override fun showMessages(messages: MutableList<Message>?) {
        if (messages == null) {
            Toast.makeText(this, "Can't fetch messages", Toast.LENGTH_SHORT).show()
            return
        }

        Log.i(SearchActivity.TAG, "Showing fetched messages")
        messagesAdapter.messages = messages
        messagesAdapter.notifyDataSetChanged()
    }

    override fun addMessage(message: Message) {
        Log.i(SearchActivity.TAG, "Showing new message")
        messagesAdapter.add(message)
    }

    companion object {
        const val TAG = "Chat Activity"
    }
}