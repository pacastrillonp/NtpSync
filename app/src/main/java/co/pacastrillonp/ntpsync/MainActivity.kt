package co.pacastrillonp.ntpsync

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.pacastrillonp.ntpsync.ui.ActualTime
import co.pacastrillonp.ntpsync.ui.theme.NtpSyncTheme
import co.pacastrillonp.ntpsync.util.difInMilliseconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

//    private val ip = "172.16.20.81"
//    private val puerto = "8080"
//    private lateinit var webSocketManager: WebSocketManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var enableImage by rememberSaveable { mutableStateOf(false) }

            NtpSyncTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        ActualTime()
                        Spacer(modifier = Modifier.size(16.dp))
                        Button(
                            onClick = {
//                                webSocketManager.sendMessage("Esta es un mensaje de prueba")

                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4EA8E9),
                                disabledContainerColor = Color(0xFF78C8F9),
                                contentColor = Color.White,
                                disabledContentColor = Color.White
                            )
                        ) {
                            Text(text = "Send Message")
                        }

                        if (enableImage) {
                            Image(
                                painter = painterResource(id = R.drawable.dragon_ball),
                                contentDescription = "dragon",
                            )
                        }
                    }
                }
            }

            LaunchedEffect(true) {
                val coroutineScope = CoroutineScope(Dispatchers.Default)
                val targetDate = "17:35:00"
                coroutineScope.launch {

                    difInMilliseconds(targetDate)?.let { milliseconds ->
                        delayInMilliseconds(milliseconds)
                    }

                    enableImage = true
                }
            }


        }

//        val webSocketListener = MyWebSocketListener()
//
//        val webSocketUrl = "ws://$ip:$puerto/websocket"
//
//        webSocketManager = WebSocketManager(webSocketListener)
//        webSocketManager.connectWebSocket(webSocketUrl)

    }

    override fun onDestroy() {
        super.onDestroy()
//        webSocketManager.disconnectWebSocket()
    }


    private suspend fun delayInMilliseconds(delay: Long) {
        delay(delay)
        Log.i("🛸", "La corrutina se ha ejecutado después de ${delay / 1000} segundos.")
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    NtpSyncTheme {
    }
}
