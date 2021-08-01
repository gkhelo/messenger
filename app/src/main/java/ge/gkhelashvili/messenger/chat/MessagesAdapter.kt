package ge.gkhelashvili.messenger.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.formatToMessageTime
import ge.gkhelashvili.messenger.model.Message

class MessagesAdapter(private val currentUserId: String) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    var messages = mutableListOf<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = if (viewType == SENT) {
            LayoutInflater.from(parent.context).inflate(R.layout.sent_message, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.received_message, parent, false)
        }
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindMessage(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.fromUser.equals(currentUserId)) {
            SENT
        } else {
            RECEIVED
        }
    }

    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val time = view.findViewById<TextView>(R.id.message_time)
        private val text = view.findViewById<TextView>(R.id.message_text)

        fun bindMessage(message: Message) {
            time.text = message.time!!.formatToMessageTime()
            text.text = message.text
        }
    }

    companion object {
        const val SENT = 0
        const val RECEIVED = 1
    }
}