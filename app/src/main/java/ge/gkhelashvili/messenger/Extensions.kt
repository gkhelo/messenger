package ge.gkhelashvili.messenger

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatToMessageTime(): String {
    val sdf= SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(this)
}

fun String.fakeMail(): String {
    return "$this@mmm.com"
}

// TODO: maybe change implementation
fun messageKey(s1: String, s2: String): String {
    if (s1 < s2) {
        return "${s1}_${s2}"
    }
    return "${s2}_${s1}"
}