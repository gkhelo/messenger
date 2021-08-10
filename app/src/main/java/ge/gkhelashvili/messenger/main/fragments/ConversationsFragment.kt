package ge.gkhelashvili.messenger.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.main.IMainView
import ge.gkhelashvili.messenger.main.MainPresenter

class ConversationsFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_conversations, container, false)
    }

}