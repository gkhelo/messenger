package ge.gkhelashvili.messenger.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ge.gkhelashvili.messenger.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG, "Created activity")
    }

    companion object {
        const val TAG = "Main Activity"
    }
}