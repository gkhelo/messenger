package ge.gkhelashvili.messenger.profile

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ge.gkhelashvili.messenger.R

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        Log.i(TAG, "Created activity")
    }

    companion object {
        const val TAG = "Profile Activity"
    }
}