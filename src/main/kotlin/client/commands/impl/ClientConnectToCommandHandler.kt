package client.commands.impl

import client.ClientConnection
import client.ClientConnectionsRepository
import client.commands.ClientCommandHandler
import java.io.DataOutputStream
import java.net.Socket

class ClientConnectToCommandHandler(
    private val clientConnectionsRepository: ClientConnectionsRepository,
) : ClientCommandHandler {

    override fun handle(command: String): Boolean {
        val splited = command.split(" ")

        return if (splited[0] == "/connectTo") {
            val address = splited[1]
            val splitedAddress = address.split(":")

            val newSocket = Socket(splitedAddress[0], splitedAddress[1].toInt())

            val clientConnection = ClientConnection(
                newSocket,
                DataOutputStream(newSocket.getOutputStream())
            )

            clientConnectionsRepository.put(address, clientConnection)

            true
        } else {
            false
        }
    }
}