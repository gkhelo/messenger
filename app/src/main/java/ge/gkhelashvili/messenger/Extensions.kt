package ge.gkhelashvili.messenger

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatToMessageTime(): String {
    val sdf= SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(this)
}