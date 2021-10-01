package client

import java.io.DataOutputStream
import java.net.Socket

data class ClientConnection(
    val socket: Socket,
    val outputStream: DataOutputStream,
)
