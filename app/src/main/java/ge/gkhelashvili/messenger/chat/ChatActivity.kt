package ge.gkhelashvili.messenger.chat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ge.gkhelashvili.messenger.R

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        Log.i(TAG, "Created activity")
    }

    companion object {
        const val TAG = "Chat Activity"
    }
}