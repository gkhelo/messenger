package ge.gkhelashvili.messenger.main.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.main.ConversationListAdapter
import ge.gkhelashvili.messenger.model.Conversation

class ConversationsFragment() : Fragment() {

    private lateinit var rvConversations: RecyclerView
    private lateinit var scrollView: NestedScrollView
    private lateinit var search: TextInputEditText
    private var mListener: OnCompleteListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_conversations, container, false)
        scrollView = view.findViewById(R.id.scrollView)
        rvConversations = view.findViewById(R.id.rvConversations)
        rvConversations.adapter = ConversationListAdapter(emptyList())
        val layout = view.findViewById<TextInputLayout>(R.id.main_search)
        search = layout.findViewById(R.id.search_edit_text)
        mListener?.onComplete()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mListener = context as OnCompleteListener
    }

    fun getConversationScrollView(): NestedScrollView {
        return scrollView
    }

    fun getConversationSearch(): TextInputEditText{
        return search
    }

    fun setInfo(conversations: List<Conversation>, index: Int) {
        (rvConversations.adapter as ConversationListAdapter).updateData(conversations, index)
    }

}

interface OnCompleteListener {
    fun onComplete()
}


