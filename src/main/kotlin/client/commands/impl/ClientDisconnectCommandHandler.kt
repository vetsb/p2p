package client.commands.impl

import client.ClientConnectionsRepository
import client.commands.ClientCommandHandler

class ClientDisconnectCommandHandler(
    private val clientConnectionsRepository: ClientConnectionsRepository
) : ClientCommandHandler {

    override fun handle(command: String): Boolean {
        val splited = command.split(" ")

        return if (splited[0] == "/disconnect") {
            val address = splited[1]

            clientConnectionsRepository.apply {
                get(address)?.apply {
                    outputStream.close()
                    socket.close()
                }
                remove(address)
            }

            true
        } else {
            false
        }
    }
}