import client.Client
import client.ClientConnectionsRepository
import client.commands.impl.ClientConnectToCommandHandler
import client.commands.impl.ClientDisconnectCommandHandler
import client.commands.impl.ClientSendCommandHandler
import client.commands.impl.UnknownCommandHandler
import server.Server
import java.net.ServerSocket
import kotlin.concurrent.thread

fun main() {
    print("Укажите порт: ")
    val port = System.`in`.bufferedReader().readLine().trim().toInt()

    val serverSocket = ServerSocket(port)

    println("Сервер поднят на ${serverSocket.inetAddress.hostAddress}:$port")

    val clientConnectionsRepository = ClientConnectionsRepository()

    val commandHandlers = listOf(
        ClientConnectToCommandHandler(clientConnectionsRepository),
        ClientDisconnectCommandHandler(clientConnectionsRepository),
        ClientSendCommandHandler(clientConnectionsRepository),
        UnknownCommandHandler(),
    )

    val server = Server(serverSocket)
    val client = Client(System.`in`.bufferedReader(), commandHandlers)

    thread {
        server.run()
    }

    thread {
        client.run()
    }
}