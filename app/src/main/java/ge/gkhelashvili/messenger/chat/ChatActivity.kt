package ge.gkhelashvili.messenger.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.main.MainActivity
import ge.gkhelashvili.messenger.model.User

class ChatActivity : AppCompatActivity(), IChatView {

    private lateinit var user: User
    private lateinit var presenter: ChatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        Log.i(TAG, "Created activity")
        init()
    }

    private fun init() {
        val userSerializable = intent.getSerializableExtra("user")
        if (userSerializable == null || userSerializable !is User) {
            Toast.makeText(this, "Can't load users", Toast.LENGTH_SHORT).show()
            return
        }

        user = userSerializable
        presenter = ChatPresenter()

        initToolbar()
    }

    private fun initToolbar() {
        initBackButton()
        initUser()
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
        if (user.avatar != null) {
            Glide
                .with(this)
                .load(presenter.getAvatarReference(user.avatar!!))
                .into(avatar)
        } else {
            avatar.setImageResource(R.drawable.avatar_image_placeholder)
        }
    }

    companion object {
        const val TAG = "Chat Activity"
    }
}