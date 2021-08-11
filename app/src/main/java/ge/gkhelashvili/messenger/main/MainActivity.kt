package ge.gkhelashvili.messenger.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.login.LoginActivity
import ge.gkhelashvili.messenger.main.fragments.ConversationsFragment
import ge.gkhelashvili.messenger.main.fragments.ProfileFragment
import ge.gkhelashvili.messenger.model.User
import ge.gkhelashvili.messenger.register.RegisterActivity
import ge.gkhelashvili.messenger.search.SearchActivity
import androidx.core.widget.NestedScrollView
import ge.gkhelashvili.messenger.main.fragments.OnCompleteListener


class MainActivity : AppCompatActivity(), IMainView, OnCompleteListener {

    private lateinit var presenter: MainPresenter
    private lateinit var viewPager: ViewPager2
    private var fragmentsList = arrayListOf(ConversationsFragment(), ProfileFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "Created activity")
        presenter = MainPresenter(this)
        initView()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
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
                presenter.getInfo(position)
            }
        })

    }

    companion object {
        const val TAG = "Main Activity"
    }

    override fun setProfileInfo(user: User) {
        (viewPager.adapter as ViewPagerAdapter).setProfileInfo(user)
    }

    override fun onSignedOut() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    fun updateButtonClicked(view: View) {
        val userInfo = (viewPager.adapter as ViewPagerAdapter).getProfileInfo()
        presenter.updateUserInfo(userInfo)
    }

    fun signOutButtonClicked(view: View){
        presenter.signOut()
    }

    fun addFabClicked(view: View) {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    override fun onComplete() {
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        val scrollView = (viewPager.adapter as ViewPagerAdapter).getScrollView()
        scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY){
                bottomAppBar.performHide()
            }else{
                bottomAppBar.performShow()
            }
        })
    }


}