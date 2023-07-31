package co.pacastrillonp.ntpsync.util

import okhttp3.*

class WebSocketManager(private val listener: WebSocketListener) {
    private var webSocket: WebSocket? = null

    fun connectWebSocket(url: String) {
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, listener)
    }

    fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    fun disconnectWebSocket() {
        webSocket?.cancel()
    }
}
