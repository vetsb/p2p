import client.Client
import client.ClientConnectionsRepository
import server.Server
import java.net.ServerSocket
import kotlin.concurrent.thread

fun main() {
    print("Укажите порт: ")
    val port = System.`in`.bufferedReader().readLine().trim().toInt()

    val serverSocket = ServerSocket(port)

    println("Сервер поднят на ${serverSocket.inetAddress.hostAddress}:$port")

    val server = Server(serverSocket)
    val client = Client(System.`in`.bufferedReader(), ClientConnectionsRepository())

    thread {
        server.run()
    }

    thread {
        client.run()
    }
}