package co.pacastrillonp.ntpsync.util


import com.instacart.library.truetime.TrueTimeRx
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getLocalCurrentTime(): String {
    val timeFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
    val horaActualMillis = System.currentTimeMillis()
    return timeFormat.format(horaActualMillis)
}

fun getCurrentTimeFromNtp(): Date? {
    return try {
        TrueTimeRx.build()
            .initializeRx("time.google.com")
            .subscribeOn(Schedulers.io())
            .subscribe()
        TrueTimeRx.now()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getCurrentTimeFromNtpS(): Date? {
    return try {
        TrueTimeRx.build()
            .withNtpHost("time.google.com")
            .initialize()
        TrueTimeRx.now()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


fun getCurrentTimeFromNtpString(): Date? {
    val currentTime = getCurrentTimeFromNtpS()
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val formattedTime = currentTime?.let { timeFormat.format(it) } ?: return null

    return try {
        timeFormat.parse(formattedTime)
    } catch (e: Exception) {
        null
    }
}


fun difInMilliseconds(targetTime: String): Long? {
    val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val currentTime = getLocalCurrentTime()

    val targetDate: Date? = simpleDateFormat.parse(targetTime)
    val actualDate: Date? = getCurrentTimeFromNtpString()
//    val actualDate: Date? = simpleDateFormat.parse(currentTime)

    val millisecondsTargetDate = targetDate?.time ?: return null
    var millisecondsActualDate = actualDate?.time ?: return null

    if (millisecondsTargetDate < millisecondsActualDate) {
        val oneDayInMilliseconds = 24 * 60 * 60 * 1000
        millisecondsActualDate += oneDayInMilliseconds
    }

    return millisecondsTargetDate - millisecondsActualDate

}