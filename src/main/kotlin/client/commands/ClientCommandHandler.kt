package client.commands

interface ClientCommandHandler {

    /**
     * @return
     * - true if handling was successfully
     * - false if you should let handling to the next handler
     */
    fun handle(command: String): Boolean
}