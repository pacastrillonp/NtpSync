package co.pacastrillonp.ntpsync.util

import android.util.Log
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage

class MqttClientManager(private val brokerUrl: String) {

    private lateinit var mqttClient: MqttClient

    init {
        connect()
    }

    private fun connect() {

        try {
            val clientId = MqttClient.generateClientId()
            mqttClient = MqttClient(brokerUrl, clientId)

            val options = MqttConnectOptions()
            options.isAutomaticReconnect = true

            mqttClient.connect(options)
        } catch (e: MqttException) {
            Log.e("🛸", e.stackTraceToString())
        }

    }

    fun publishMessage(topic: String, message: String) {
        val mqttMessage = MqttMessage(message.toByteArray())
        mqttClient.publish(topic, mqttMessage)
    }

    fun subscribeToTopic(topic: String, callback: MqttCallbackExtended) {
        mqttClient.setCallback(callback)
        mqttClient.subscribe(topic)
    }

    fun disconnect() {
        mqttClient.disconnect()
    }
}
