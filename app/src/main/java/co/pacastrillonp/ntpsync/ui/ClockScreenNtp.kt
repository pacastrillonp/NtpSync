package co.pacastrillonp.ntpsync.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import co.pacastrillonp.ntpsync.util.getCurrentTimeFromNtp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun ClockScreenNtp() {

    var currentTime by remember { mutableStateOf<Date?>(null) }

    LaunchedEffect(true) {
        while (true) {
            currentTime = getCurrentTimeFromNtp()
            delay(500)
        }
    }
    Box {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedTime = currentTime?.let { dateFormat.format(it) } ?: "N/A"

        Text(
            text = "Hora actual: $formattedTime",
            style = MaterialTheme.typography.titleLarge
        )
    }

}