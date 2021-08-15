package ge.gkhelashvili.messenger.main

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.login.LoginActivity
import ge.gkhelashvili.messenger.main.fragments.ConversationsFragment
import ge.gkhelashvili.messenger.main.fragments.ProfileFragment
import ge.gkhelashvili.messenger.model.User
import ge.gkhelashvili.messenger.search.SearchActivity
import androidx.core.widget.NestedScrollView
import com.google.android.material.textfield.TextInputEditText
import ge.gkhelashvili.messenger.main.fragments.OnCompleteListener
import ge.gkhelashvili.messenger.model.Conversation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity(), IMainView, OnCompleteListener, CoroutineScope {

    private lateinit var presenter: MainPresenter
    private lateinit var viewPager: ViewPager2
    private lateinit var username: String
    private var fragmentsList = arrayListOf(ConversationsFragment(), ProfileFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "Created activity")
        presenter = MainPresenter(this)

        if (!presenter.isUserSignedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }

        setContentView(R.layout.activity_main)
        initView()
        presenter.getConversationsInfo()

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
                    1 -> {
                        bottomNav.selectedItemId = R.id.ic_profile
                        presenter.getProfileInfo()
                        findViewById<BottomAppBar>(R.id.bottomAppBar).performShow()
                    }
                }
            }
        })

    }

    companion object {
        const val TAG = "Main Activity"
        const val SELECT_PICTURE = 200
    }

    override fun setProfileInfo(user: User) {
        username = user.username.toString()
        (viewPager.adapter as ViewPagerAdapter).setProfileInfo(user)
    }

    override fun onSignedOut() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun showInfoFetchError() {
        Toast.makeText(this,"ERROR", Toast.LENGTH_SHORT).show()
    }

    override fun showConversations(conversations: List<Conversation>, index: Int) {
        (viewPager.adapter as ViewPagerAdapter).setConversationsInfo(conversations, index)
    }

    fun updateButtonClicked(view: View) {
        val userInfo = (viewPager.adapter as ViewPagerAdapter).getProfileInfo()
        presenter.updateUserInfo(userInfo, username)
        val bitmap = (viewPager.adapter as ViewPagerAdapter).getProfileImageBitmap()
        presenter.uploadImage(bitmap)
    }

    fun photoClicked(view: View){
        if(checkPermissions()){
            val i = Intent(Intent.ACTION_PICK)
            i.type = "image/*"
            startActivityForResult(i, SELECT_PICTURE)
        }
    }

    private fun checkPermissions(): Boolean {
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())

        val listPermissionsNeeded = arrayListOf<String>()
        for (permission in listOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)) {
            if (this.let { ContextCompat.checkSelfPermission(it, permission) } != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission)
            }
        }

        if (listPermissionsNeeded.isNotEmpty()) {
            this.let { ActivityCompat.requestPermissions(it, listPermissionsNeeded.toTypedArray(), 0) }
            return false
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                val selectedImageUri = data?.data!!
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                val bitmapDrawable = BitmapDrawable(bitmap)
                (viewPager.adapter as ViewPagerAdapter).setProfileImage(bitmapDrawable)
            }
        }
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

        val search = (viewPager.adapter as ViewPagerAdapter).getSearch()
        initSearch(search)
    }

    private fun initSearch(search: TextInputEditText){
        val watcher = object : TextWatcher {
            private var name = ""

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString().trim()
                if (text == name) {
                    return
                }

                name = text
                launch {
                    delay(300)

                    if (text != name)
                        return@launch

                    if (name.isEmpty() || name.length > 3){
                        presenter.setConversationsInfo(name)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        }

        search.addTextChangedListener(watcher)
    }

    override val coroutineContext: CoroutineContext = Dispatchers.Main
}