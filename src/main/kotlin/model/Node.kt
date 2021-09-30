package model

import java.net.ServerSocket

data class Node(
    val connections: Map<String, Connection>,
    val socket: ServerSocket
)