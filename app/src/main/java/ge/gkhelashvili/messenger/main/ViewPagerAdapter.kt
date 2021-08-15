package ge.gkhelashvili.messenger.main

import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ge.gkhelashvili.messenger.main.fragments.ConversationsFragment
import ge.gkhelashvili.messenger.main.fragments.ProfileFragment
import ge.gkhelashvili.messenger.model.Conversation
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

    fun getScrollView(): NestedScrollView {
        return (fragmentsList[0] as ConversationsFragment).getConversationScrollView()
    }

    fun setConversationsInfo(conversations: List<Conversation>, index: Int) {
        (fragmentsList[0] as ConversationsFragment).setInfo(conversations, index)
    }
}