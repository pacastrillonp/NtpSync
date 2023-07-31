package co.pacastrillonp.ntpsync.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.pacastrillonp.ntpsync.util.getLocalCurrentTime
import kotlinx.coroutines.delay

@Composable
fun ActualTime() {
    var actualTime by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        while (true) {
            actualTime = getLocalCurrentTime()
            delay(500)
        }
    }

    Text(
        text = actualTime,
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.titleMedium
    )
}