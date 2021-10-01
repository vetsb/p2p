package client

import client.commands.ClientCommandHandler
import client.commands.impl.UnknownCommandHandler
import java.io.BufferedReader

class Client(
    private val commandsReader: BufferedReader,
    private val commandHandlers: List<ClientCommandHandler>,
) {

    init {
        val unknownCommandHandlerIndexedValue = commandHandlers.withIndex()
            .firstOrNull { it.value is UnknownCommandHandler }

        requireNotNull(unknownCommandHandlerIndexedValue) {
            println("You must add unknown command handler")
        }

        require(unknownCommandHandlerIndexedValue.index == commandHandlers.lastIndex) {
            println("Unknown command handler must be the last")
        }
    }

    fun run() {
        loop@ while (true) {
            val command = commandsReader.readLine()

            for (i in commandHandlers.indices) {
                if (commandHandlers[i].handle(command)) {
                    continue@loop
                }
            }
        }
    }
}