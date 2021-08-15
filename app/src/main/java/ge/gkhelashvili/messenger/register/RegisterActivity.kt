package ge.gkhelashvili.messenger.register

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.main.MainActivity
import ge.gkhelashvili.messenger.model.User

class RegisterActivity : AppCompatActivity(), IRegisterView {

    private lateinit var presenter: IRegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        Log.i(TAG, "Created activity")
        init()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    private fun init() {
        presenter = RegisterPresenter(this)

        initSignUp()
    }

    private fun initSignUp() {
        val usernameEditText = findViewById<EditText>(R.id.register_nickname)

        findViewById<Button>(R.id.register_sign_up).setOnClickListener {
            val username = usernameEditText.text.toString()
            if (username.isEmpty()) {
                Toast.makeText(this, "Username shouldn't be empty", Toast.LENGTH_SHORT).show()
            } else {
                presenter.validateUsername(username)
            }
        }
    }

    override fun onUsernameValidated(username: String?, isValid: Boolean) {
        if (username == null) {
            Toast.makeText(this, "Can't validate username", Toast.LENGTH_SHORT).show()
            return
        }
        if (!isValid) {
            Toast.makeText(this, "Username is already used", Toast.LENGTH_SHORT).show()
            return
        }

        val password = findViewById<EditText>(R.id.register_password).text.toString()
        if (password.isEmpty()) {
            Toast.makeText(this, "Password shouldn't be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val profession = findViewById<EditText>(R.id.register_profession).text.toString()
        if (profession.isEmpty()) {
            Toast.makeText(this, "Profession shouldn't be empty", Toast.LENGTH_SHORT).show()
            return
        }


        val registerAvatar = findViewById<ImageView>(R.id.register_avatar)
        registerAvatar.isDrawingCacheEnabled = true
        registerAvatar.buildDrawingCache()
        val bitmap = (registerAvatar.drawable as BitmapDrawable).bitmap
        presenter.registerUser(username, password, profession, bitmap)
    }

    override fun onUserRegistered(user: User?, errorMessage: String?) {
        if (user == null) {
            Toast.makeText(this, "Can't register user: $errorMessage", Toast.LENGTH_SHORT).show()
            return
        }
        startActivity(Intent(this, MainActivity::class.java))
    }

    companion object {
        const val TAG = "Register Activity"
    }
}