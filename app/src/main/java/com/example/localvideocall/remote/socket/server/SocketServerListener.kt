package com.example.localvideocall.remote.socket.server

import com.example.localvideocall.utils.MessageModel

interface SocketServerListener {
    fun onSocketServerNewMessage(message: MessageModel)
    fun onStartServer(port:Int)
    fun onClientDisconnected()
}