package com.example.localvideocall.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.localvideocall.remote.socket.client.SocketClient
import com.example.localvideocall.remote.socket.client.SocketClientListener
import com.example.localvideocall.utils.MessageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val application: Application,
    private val socketClient: SocketClient
):ViewModel(), SocketClientListener {
    fun init(serverAddress:String, onError:() -> Unit){
        //check if client socket is not working then we have to close clients ui
        startSocketClient(serverAddress,onError)
    }

    private fun startSocketClient(serverAddress:String,onError:() -> Unit){
        socketClient.init(serverAddress,this@ClientViewModel,onError)
    }

    override fun onSocketClientOpened() {
       Log.d("TAG", "onSocketClientOpened: ")
    }

    override fun onSocketClientMessage(messageModel: MessageModel) {
        TODO("Not yet implemented")
    }
}