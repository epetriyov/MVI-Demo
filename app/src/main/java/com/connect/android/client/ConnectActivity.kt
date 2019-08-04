package com.connect.android.client

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.connect.android.client.extensions.setRoot
import com.connect.android.client.modules.base.ActivityResultListener
import com.connect.android.client.modules.base.ActivityResultObservable
import com.connect.android.client.modules.start.StartController

class ConnectActivity : AppCompatActivity(), ActivityResultObservable {

    private lateinit var router: Router

    private val onActivityResultListeners = mutableListOf<ActivityResultListener>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_connect)
        router = Conductor.attachRouter(this, findViewById(R.id.container), savedInstanceState)
        router.setRoot(StartController())
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResultListeners.forEach { it.onActivityResult(requestCode, resultCode, data) }
    }

    override fun addOnActivityResultListener(onActivityResultListener: ActivityResultListener) {
        onActivityResultListeners.add(onActivityResultListener)
    }

    override fun removeOnActivityResultListener(onActivityResultListener: ActivityResultListener) {
        onActivityResultListeners.remove(onActivityResultListener)
    }
}