package client

import java.io.BufferedReader
import java.io.DataOutputStream
import java.net.Socket

class Client(
    private val commandsReader: BufferedReader
) {

    private val connections = mutableMapOf<String, ClientConnection>()

    fun run() {
        while (true) {
            val line = commandsReader.readLine()
            val splited = line.split(" ")

            when (splited[0]) {
                "/connectTo" -> {
                    val address = splited[1]
                    val splitedAddress = address.split(":")

                    val newSocket = Socket(splitedAddress[0], splitedAddress[1].toInt())

                    val clientConnection = ClientConnection(
                        newSocket,
                        DataOutputStream(newSocket.getOutputStream())
                    )

                    connections[address] = clientConnection
                }
                "/disconnect" -> {
                    val address = splited[1]

                    connections.apply {
                        get(address)?.apply {
                            outputStream.close()
                            socket.close()
                        }
                        remove(address)
                    }
                }
                "/send" -> {
                    val address = splited[1]
                    val message = splited[2].toByteArray()

                    connections[address]?.let { connection ->
                        connection.outputStream.apply {
                            writeInt(message.size)
                            write(message)
                        }
                    }
                }
                else -> {
                    println("Неизвестная команда")
                }
            }
        }
    }
}