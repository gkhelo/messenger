package ge.gkhelashvili.messenger.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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

    private fun init() {
        presenter = RegisterPresenter(this)

        initSignUp()
    }

    private fun initSignUp() {
        val username = findViewById<EditText>(R.id.register_nickname)

        findViewById<Button>(R.id.register_sign_up).setOnClickListener {
            presenter.validateUsername(username.text.toString())
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

        Log.i(TAG, "Username is valid")

        val password = findViewById<EditText>(R.id.register_password)
        val profession = findViewById<EditText>(R.id.register_profession)
        presenter.registerUser(username, password.text.toString(), profession.text.toString())
    }

    override fun onUserRegistered(user: User?) {
        if (user == null) {
            Toast.makeText(this, "Can't register user", Toast.LENGTH_SHORT).show()
            return
        }
        startActivity(Intent(this, MainActivity::class.java))
    }

    companion object {
        const val TAG = "Register Activity"
    }
}