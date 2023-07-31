package co.pacastrillonp.ntpsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import co.pacastrillonp.ntpsync.ui.ActualTime
import co.pacastrillonp.ntpsync.ui.theme.NtpSyncTheme
import co.pacastrillonp.ntpsync.util.MyWebSocketListener
import co.pacastrillonp.ntpsync.util.WebSocketManager

class MainActivity : ComponentActivity() {

    private val ip = "192.168.1.100"
    private val puerto = "8080"
    private lateinit var webSocketManager: WebSocketManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            NtpSyncTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ActualTime()
                }
            }
        }

        val webSocketListener = MyWebSocketListener()

        val webSocketUrl = "ws://$ip:$puerto/websocket"

        webSocketManager = WebSocketManager(webSocketListener)
        webSocketManager.connectWebSocket(webSocketUrl)

    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketManager.disconnectWebSocket()
    }
}
