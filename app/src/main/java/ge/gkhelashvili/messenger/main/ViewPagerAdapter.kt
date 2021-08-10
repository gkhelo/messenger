package ge.gkhelashvili.messenger.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ge.gkhelashvili.messenger.main.fragments.ProfileFragment
import ge.gkhelashvili.messenger.model.User

class ViewPagerAdapter(activity: FragmentActivity, private val fragmentsList: ArrayList<Fragment>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return fragmentsList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position]
    }

    fun setProfileInfo(user: User) {
        (fragmentsList[1] as ProfileFragment).setInfo(user)
    }

    fun getProfileInfo(): User{
        return (fragmentsList[1] as ProfileFragment).getInfo()
    }
}