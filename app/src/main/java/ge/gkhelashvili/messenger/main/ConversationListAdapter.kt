package ge.gkhelashvili.messenger.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.model.Conversation

class ConversationListAdapter(var list: List<Conversation>): RecyclerView.Adapter<ConversationItemViewHolder>() {

    var convs = arrayListOf(Conversation("Sayed Effiaz", "5 min", "On my way home but i needed to stop by the book store to...", null),
        Conversation("Sanjida Akter", "15 min", "On my way home but i needed to stop by the book store to...", null),
        Conversation("Sayed Effiaz", "1 hour", "On my way home but i needed to stop by the book store to...", null),
        Conversation("Tour de Bhutan", "5 min", "On my way home but i needed to stop by the book store to...", null),
        Conversation("Sayed Effiaz", "1 hour", "On my way home but i needed to stop by the book store to...", null),
        Conversation("Sayed Effiaz", "1 hour", "On my way home but i needed to stop by the book store to...", null),
        Conversation("Saba", "1 hour", "On my way home but i needed to stop by the book store to...", null),
        Conversation("Giorgi", "5 min", "On my way home but i needed to stop by the book store to...", null),
        Conversation("Dato", "15 min", "On my way home but i needed to stop by the book store to...", null),
        Conversation("Giorgi", "10 min", "On my way home but i needed to stop by the book store to...", null),
        Conversation("Dato", "11 min", "On my way home but i needed to stop by the book store to...", null),
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.conversation_list_item, parent, false)
        return ConversationItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversationItemViewHolder, position: Int) {
        holder.bindConversation(convs[position])
    }

    override fun getItemCount(): Int {
        return convs.size
    }
}

class ConversationItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    private val conversationImage = itemView.findViewById<ImageView>(R.id.conversationImage)
    private val nameText = itemView.findViewById<TextView>(R.id.nameText)
    private val lastMessageText = itemView.findViewById<TextView>(R.id.lastMessageText)
    private val timeText = itemView.findViewById<TextView>(R.id.timeText)

    fun bindConversation(conversation: Conversation) {
        nameText.text = conversation.toUser
        lastMessageText.text = conversation.lastMessage
        timeText.text = conversation.lastMessagetime
        conversationImage.setImageResource(R.drawable.avatar_image_placeholder)
    }

}