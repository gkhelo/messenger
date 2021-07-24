package ge.gkhelashvili.messenger.search

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ge.gkhelashvili.messenger.R

class SearchActivity : AppCompatActivity(), ISearchView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        Log.i(TAG, "Created activity")
    }

    companion object {
        const val TAG = "Search Activity"
    }
}