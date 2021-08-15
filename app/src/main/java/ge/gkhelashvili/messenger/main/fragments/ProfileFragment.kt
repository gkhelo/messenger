package ge.gkhelashvili.messenger.main.fragments

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import ge.gkhelashvili.messenger.R
import ge.gkhelashvili.messenger.model.User
import java.io.File

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
//        val imageFile = File(imagePath)
//        val imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
//        profileImage.background = null
//        profileImage.setImageBitmap(circleBitmap(imageBitmap))
    }

    private fun circleBitmap(bitmap: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawCircle((bitmap.width / 2).toFloat(), (bitmap.height / 2).toFloat(), (bitmap.width / 2).toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    fun getBitmap(): Bitmap? {
        profileImage.isDrawingCacheEnabled = true
        profileImage.buildDrawingCache()
        val bitmap = (profileImage.drawable as BitmapDrawable).bitmap
        return bitmap
    }

}