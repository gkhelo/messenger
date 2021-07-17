package ge.gkhelashvili.messenger.register

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ge.gkhelashvili.messenger.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        Log.i(TAG, "Created activity")
    }

    companion object {
        const val TAG = "Register Activity"
    }
}