package ge.gkhelashvili.messenger.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.main.MainActivity
import ge.gkhelashvili.messenger.register.RegisterActivity

class LoginActivity : AppCompatActivity(), ILoginView {
    private lateinit var presenter: LoginPresenter
    private lateinit var nickname: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        presenter = LoginPresenter(this)
        Log.i(TAG, "Created activity")
    }

    override fun onDestroy(){
        presenter.detachView()
        super.onDestroy()
    }

    private fun initView(){
        nickname = findViewById(R.id.editTextTextLoginNickname)
        password = findViewById(R.id.editTextTextLoginPassword)
    }

    fun loginButtonClicked(view: View) {
        presenter.validateCredentials(nickname.text.toString(), password.text.toString())
    }

    override fun showMainPage(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun showEmptyFieldError() {
        Toast.makeText(this,"One or more of the fields are empty", Toast.LENGTH_SHORT).show()
        nickname.setText("")
        password.setText("")
    }

    override fun showAuthorizationError(){
        Toast.makeText(this,"Authorization unsuccessful", Toast.LENGTH_SHORT).show()
        nickname.setText("")
        password.setText("")
    }

    fun signUpButtonClicked(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    companion object {
        const val TAG = "Login Activity"
    }
}