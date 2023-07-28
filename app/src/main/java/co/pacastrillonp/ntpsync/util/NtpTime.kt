package co.pacastrillonp.ntpsync.util


import com.instacart.library.truetime.TrueTimeRx
import io.reactivex.schedulers.Schedulers
import java.util.*

fun getCurrentTime(): Date? {
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