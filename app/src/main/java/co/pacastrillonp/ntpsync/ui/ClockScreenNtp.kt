package co.pacastrillonp.ntpsync.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import co.pacastrillonp.ntpsync.util.getCurrentTime
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun ClockScreenNtp() {
    // Estado para almacenar la hora actualizada
    var currentTime by remember { mutableStateOf<Date?>(null) }

    // Función para actualizar la hora cada 10 segundos
    LaunchedEffect(true) {
        while (true) {
            currentTime = getCurrentTime()
            delay(200) // 10 segundos
        }
    }

    Scaffold(
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val formattedTime = currentTime?.let { dateFormat.format(it) } ?: "N/A"

                Text(
                    text = "Hora actual: $formattedTime",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    )
}