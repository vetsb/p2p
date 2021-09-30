package model

import java.io.DataOutputStream
import java.net.Socket

data class Connection(
    val socket: Socket,
    val outputStream: DataOutputStream,
)
