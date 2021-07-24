package ge.gkhelashvili.messenger.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.model.User

class UsersAdapter(val presenter: ISearchPresenter) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    var users = listOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_info, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindUser(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class UserViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val name = view.findViewById<TextView>(R.id.user_info_name)
        private val profession = view.findViewById<TextView>(R.id.user_info_profession)
        private val avatar = view.findViewById<ImageView>(R.id.user_info_avatar)

        fun bindUser(user: User) {
            name.text = user.username
            profession.text = user.profession

            if (user.avatar != null) {
                Glide
                    .with(view)
                    .load(presenter.getAvatarReference(user.avatar))
                    .into(avatar)
            }
        }
    }
}