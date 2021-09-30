package server

import java.io.DataInputStream
import java.io.EOFException
import java.net.Socket

class ServerSocketWorker(
    private val socket: Socket
) : Thread() {

    override fun run() {
        val dataInputStream = DataInputStream(socket.getInputStream())

        while (true) {
            try {
                val messageSize = dataInputStream.readInt()
                val bytes = dataInputStream.readNBytes(messageSize)
                val line = String(bytes)

                println(line)
            } catch (e: EOFException) {
                println("Socket ${socket.inetAddress.hostAddress}:${socket.localPort} went offline")
                e.printStackTrace()
                break
            }
        }

        dataInputStream.close()
        socket.close()
    }
}