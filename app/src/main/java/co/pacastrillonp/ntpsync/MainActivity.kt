package co.pacastrillonp.ntpsync

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.pacastrillonp.ntpsync.ui.theme.NtpSyncTheme
import co.pacastrillonp.ntpsync.util.MqttClientManager
import co.pacastrillonp.ntpsync.util.getCurrentTime
import kotlinx.coroutines.delay
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val brokerUrl =
        "172.16.20.49:1883" // Reemplaza con la dirección IP del servidor MQTT
    private val broadcastTopic = "broadcast_topic"

    private lateinit var mqttClientManager: MqttClientManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            NtpSyncTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ClockScreen()
                }
            }
        }

        mqttClientManager = MqttClientManager(brokerUrl)

        // Suscribirse al canal de difusión (broadcast)
        mqttClientManager.subscribeToTopic(broadcastTopic, object : MqttCallbackExtended {
            override fun connectionLost(cause: Throwable?) {
                // Lógica en caso de pérdida de conexión
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                // Lógica para procesar el mensaje recibido
                message?.let {
                    val receivedData = String(it.payload)
                    runOnUiThread {
                        // Actualizar la interfaz de usuario con el mensaje recibido
                        // Por ejemplo, muestra el mensaje en un TextView
                        Log.d("🛸", "receivedData: $receivedData")
                    }
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                // Lógica en caso de entrega completa
            }

            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                // Lógica en caso de conexión completa
            }
        })

        // Publicar un mensaje en el canal de difusión
        val message = "Hola a todos los dispositivos!"
        mqttClientManager.publishMessage(broadcastTopic, message)

    }

    override fun onDestroy() {
        super.onDestroy()
        // Desconectar el cliente MQTT al destruir la actividad
        mqttClientManager.disconnect()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun ClockScreen() {
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        ClockScreen()
    }
}
