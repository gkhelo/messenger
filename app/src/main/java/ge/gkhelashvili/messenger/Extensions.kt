package ge.gkhelashvili.messenger

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatToMessageTime(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(this)
}

fun String.fakeMail(): String {
    return "$this@mmm.com"
}

fun Date.getTimeDifference(): String {
    var diff = System.currentTimeMillis() - this.time
    if (diff < 3600000){
        diff /= 60000
        return "$diff min"
    }else if (diff < 86400000) {
        diff /= 3600000
        return "$diff hour"
    }else{
        val months = arrayOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")
        return "${this.date} ${months[this.month]}"
    }
}

// TODO: maybe change implementation
fun messageKey(s1: String, s2: String): String {
    if (s1 < s2) {
        return "${s1}_${s2}"
    }
    return "${s2}_${s1}"
}