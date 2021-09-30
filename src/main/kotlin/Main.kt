import model.Connection
import model.Node
import server.Server
import java.io.DataOutputStream
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

lateinit var node: Node

fun main() {
    print("Укажите порт: ")
    val port = System.`in`.bufferedReader().readLine().trim().toInt()

    val serverSocket = ServerSocket(port)

    println("Сервер поднят на ${serverSocket.inetAddress.hostAddress}:$port")

    node = Node(
        connections = emptyMap(),
        socket = serverSocket
    )

    val server = Server(serverSocket)

    thread {
        server.run()
    }

    thread {
        handleClient()
    }
}

fun handleClient() {
    val systemInReader = System.`in`.bufferedReader()

    while (true) {
        val line = systemInReader.readLine()
        val splited = line.split(" ")

        when (splited[0]) {
            "/connectTo" -> {
                val address = splited[1]
                val splitedAddress = address.split(":")

                val newSocket = Socket(splitedAddress[0], splitedAddress[1].toInt())

                val connection = Connection(
                    newSocket,
                    DataOutputStream(newSocket.getOutputStream())
                )

                node = node.copy(
                    connections = node.connections.toMutableMap().apply {
                        put(address, connection)
                    }
                )
            }
            "/disconnect" -> {
                val address = splited[1]

                node = node.copy(
                    connections = node.connections.toMutableMap().apply {
                        get(address)?.apply {
                            outputStream.close()
                            socket.close()
                        }
                        remove(address)
                    }
                )
            }
            "/send" -> {
                val address = splited[1]
                val message = splited[2].toByteArray()

                val connection = node.connections[address] ?: continue

                connection.outputStream.apply {
                    writeInt(message.size)
                    write(message)
                }
            }
            else -> {
                println("Неизвестная команда")
            }
        }
    }
}