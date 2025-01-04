package com.example.localvideocall.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localvideocall.remote.socket.server.SocketServer
import com.example.localvideocall.remote.socket.server.SocketServerListener
import com.example.localvideocall.utils.MessageModel
import com.example.localvideocall.utils.getWifiIPAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HostViewModel @Inject constructor(
    private val application: Application,
    private val socketServer: SocketServer
) :ViewModel(), SocketServerListener {
    //socket variable
    private var ipAddress:String? = null

    //states
    val hostAddressState:MutableStateFlow<String?> = MutableStateFlow(null)


    //to check if host is started successfully or not
    fun init(done:(Boolean) -> Unit){
        ipAddress = getWifiIPAddress(application)
        if (ipAddress == null){
            done(false)
            return

        }
        startSocketServer()
    }

    private fun startSocketServer(){
        socketServer.init(this@HostViewModel)
    }

    override fun onCleared() {
        super.onCleared()
        socketServer.onDestroy()
    }


    override fun onSocketServerNewMessage(message: MessageModel) {

    }

    override fun onStartServer(port: Int) {
        viewModelScope.launch {
            hostAddressState.emit("Host Address: $ipAddress:$port")
        }
    }

    override fun onClientDisconnected() {

    }
}