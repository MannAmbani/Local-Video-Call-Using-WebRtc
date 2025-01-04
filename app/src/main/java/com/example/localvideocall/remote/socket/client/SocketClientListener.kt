package com.example.localvideocall.remote.socket.client

import com.example.localvideocall.utils.MessageModel

interface SocketClientListener {
    fun onSocketClientOpened()
    fun onSocketClientMessage(messageModel: MessageModel)
}