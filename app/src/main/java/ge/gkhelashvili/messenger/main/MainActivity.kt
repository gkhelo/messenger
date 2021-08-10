package ge.gkhelashvili.messenger.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.main.fragments.ConversationsFragment
import ge.gkhelashvili.messenger.main.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private var fragmentsList = arrayListOf(ConversationsFragment(), ProfileFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "Created activity")
        initView()
    }

    private fun initView() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.background = null
        bottomNav.menu.getItem(1).isEnabled = false

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(this, fragmentsList)
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_home -> viewPager.currentItem = 0
                R.id.ic_profile -> viewPager.currentItem = 1
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position){
                    0 -> bottomNav.selectedItemId = R.id.ic_home
                    1 -> bottomNav.selectedItemId = R.id.ic_profile
                }
            }
        })

    }

    companion object {
        const val TAG = "Main Activity"
    }
}