package client

import java.util.concurrent.ConcurrentHashMap

class ClientConnectionsRepository {

    private val connections = ConcurrentHashMap<String, ClientConnection>()

    fun get(address: String): ClientConnection? =
        connections[address]

    fun put(address: String, connection: ClientConnection) {
        connections[address] = connection
    }

    fun remove(address: String) {
        connections.remove(address)
    }
}