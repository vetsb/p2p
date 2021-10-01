package client.commands.impl

import client.ClientConnectionsRepository
import client.commands.ClientCommandHandler

class ClientSendCommandHandler(
    private val clientConnectionsRepository: ClientConnectionsRepository
) : ClientCommandHandler {

    override fun handle(command: String): Boolean {
        val splited = command.split(" ")

        return if (splited[0] == "/send") {
            val address = splited[1]
            val message = splited[2].toByteArray()

            clientConnectionsRepository.get(address)?.let { clientConnection ->
                clientConnection.outputStream.apply {
                    writeInt(message.size)
                    write(message)
                }
            }

            true
        } else {
            false
        }
    }
}