package server

import java.net.ServerSocket

class Server(
    private val serverSocket: ServerSocket
) {

    fun run() {
        while (true) {
            val socket = runCatching {
                serverSocket.accept()
            }.getOrNull() ?: continue

            val serverSocketWorker = ServerSocketWorker(socket)

            serverSocketWorker.run()
        }
    }
}