package client.commands.impl

import client.commands.ClientCommandHandler

class UnknownCommandHandler : ClientCommandHandler {

    override fun handle(command: String): Boolean {
        IllegalArgumentException("Неизвестная команда = $command").printStackTrace()
        return true
    }
}