package com.example.localvideocall.remote.socket.server

import com.example.localvideocall.utils.MessageModel
import com.google.gson.Gson
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Exception
import java.net.InetSocketAddress
import javax.inject.Inject

class SocketServer @Inject constructor(private val gson: Gson){
    private var socketserver:WebSocketServer?=null
    private var socketServerPort = 3031
    private var memberToCall:WebSocket?=null

    fun init(listener:SocketServerListener){
        if (socketserver == null){
            socketserver = object :WebSocketServer(InetSocketAddress(socketServerPort)){
                override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
                    //runs when a client connects and will have reference of client
                    //we can send data to clint using parameter websocket

                    //if no one is connected then we will store this connection
                    if (memberToCall == null){
                        memberToCall = conn
                    }
                }

                override fun onClose(
                    conn: WebSocket?,
                    code: Int,
                    reason: String?,
                    remote: Boolean
                ) {
                    //if member is disconnected then we will set member to null
                    if(conn == memberToCall){
                        memberToCall = null
                        listener.onClientDisconnected()
                    }

                }

                override fun onMessage(conn: WebSocket?, message: String?) {
                   //get and pass message to socketserver
                    runCatching {
                       gson.fromJson(message,MessageModel::class.java)
                    }.onSuccess {
                        listener.onSocketServerNewMessage(it)
                    }
                }

                override fun onError(conn: WebSocket?, ex: Exception?) {
                   //some time if we close the app the server os not able to resume the socket so we will re initialize
                    if (ex?.message=="Address already in use"){
                        socketServerPort++
                        onDestroy()
                        init(listener)
                    }
                }

                override fun onStart() {
                   listener.onStartServer(socketServerPort)
                }

            }.apply {
                start()
            }
        }
    }

    fun onDestroy() = runCatching {
        socketserver?.stop()
        socketserver = null

    }

    fun sendDataToClient(messageModel: MessageModel){
        kotlin.runCatching {
            memberToCall?.let { member ->
                val jsonModel = gson.toJson(messageModel)
                member.send(jsonModel)


            }
        }
    }
}