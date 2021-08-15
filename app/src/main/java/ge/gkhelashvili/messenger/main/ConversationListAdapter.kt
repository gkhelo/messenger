package ge.gkhelashvili.messenger.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.chat.ChatActivity
import ge.gkhelashvili.messenger.model.Conversation

class ConversationListAdapter(var list: List<Conversation>): RecyclerView.Adapter<ConversationItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.conversation_list_item, parent, false)
        return ConversationItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversationItemViewHolder, position: Int) {
        holder.bindConversation(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(data: List<Conversation>, index: Int){
        list = data
        if (index == - 1){
            notifyItemInserted(0)
        }else{
            notifyItemRemoved(index)
            notifyItemInserted(0)
        }
    }
}

class ConversationItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    private val conversationImage = itemView.findViewById<ImageView>(R.id.conversationImage)
    private val nameText = itemView.findViewById<TextView>(R.id.nameText)
    private val lastMessageText = itemView.findViewById<TextView>(R.id.lastMessageText)
    private val timeText = itemView.findViewById<TextView>(R.id.timeText)

    fun bindConversation(conversation: Conversation) {
        nameText.text = conversation.toUser!!.username
        if (conversation.lastMessage?.length!! > 41){
            var newStr = conversation.lastMessage.dropLast(conversation.lastMessage.length - 38)
            newStr = "$newStr..."
            lastMessageText.text = newStr
        }else{
            lastMessageText.text = conversation.lastMessage
        }
        timeText.text = conversation.lastMessagetime
        conversationImage.setImageResource(R.drawable.avatar_image_placeholder)

        itemView.setOnClickListener {
            val chatIntent = Intent(itemView.context, ChatActivity::class.java).apply {
                putExtra("user", conversation.toUser)
            }
            itemView.context.startActivity(chatIntent)
        }
    }

}