package co.pacastrillonp.ntpsync.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ActualTime() {
    var actualTime by remember { mutableStateOf("") }
    val timeFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    LaunchedEffect(true) {
        while (true) {
            val horaActualMillis = System.currentTimeMillis()
            actualTime = timeFormat.format(horaActualMillis)
            delay(500)
        }
    }

    Text(
        text = actualTime,
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.titleMedium
    )
}