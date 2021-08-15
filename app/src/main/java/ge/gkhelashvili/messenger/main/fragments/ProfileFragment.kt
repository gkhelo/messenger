package ge.gkhelashvili.messenger.main.fragments

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.glide.GlideApp
import ge.gkhelashvili.messenger.model.User

class ProfileFragment() : Fragment() {

    private lateinit var profileImage: ImageView
    private lateinit var name: EditText
    private lateinit var profession: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        profileImage = view.findViewById(R.id.profileImage)
        name = view.findViewById(R.id.editTextTextProfileName)
        profession = view.findViewById(R.id.editTextTextProfileProfession)
        return view
    }

    fun setInfo(user: User) {
        name.setText(user.username)
        profession.setText(user.profession)
    }

    fun getInfo(): User{
        return User(username = name.text.toString(), profession = profession.text.toString())
    }

    fun setImage(imagePath: BitmapDrawable) {
        Glide.with(this).load(imagePath).circleCrop().into(profileImage)
    }


    fun getBitmap(): Bitmap? {
        profileImage.isDrawingCacheEnabled = true
        profileImage.buildDrawingCache()
        return (profileImage.drawable as BitmapDrawable).bitmap
    }

    fun loadImage(pictureRef: StorageReference) {
        GlideApp.with(this)
            .load(pictureRef)
            .circleCrop()
            .into(profileImage)
    }

}