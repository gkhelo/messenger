package ge.gkhelashvili.messenger

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ge.gkhelashvili.messenger.chat.ChatActivity
import ge.gkhelashvili.messenger.login.LoginActivity
import ge.gkhelashvili.messenger.main.MainActivity
import ge.gkhelashvili.messenger.profile.ProfileActivity
import ge.gkhelashvili.messenger.register.RegisterActivity
import ge.gkhelashvili.messenger.search.SearchActivity


class MessengerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)

        initButtons()
    }

    private fun initButtons() {
        initButton(R.id.login_button, LoginActivity::class.java)
        initButton(R.id.register_button, RegisterActivity::class.java)
        initButton(R.id.main_button, MainActivity::class.java)
        initButton(R.id.search_button, SearchActivity::class.java)
        initButton(R.id.profile_button, ProfileActivity::class.java)
        initButton(R.id.chat_button, ChatActivity::class.java)
    }

    private fun initButton(buttonId: Int, activityClass: Class<out AppCompatActivity>) {
        findViewById<Button>(buttonId).setOnClickListener {
            startActivity(Intent(this, activityClass))
        }
    }
}