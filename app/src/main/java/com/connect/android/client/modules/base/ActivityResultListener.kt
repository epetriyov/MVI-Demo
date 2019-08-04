package com.connect.android.client.modules.base

import android.content.Intent

interface ActivityResultListener {
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}