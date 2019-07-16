package com.connect.android.client.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.connect.android.client.BuildConfig.APPLICATION_ID


fun Context.openPermissionsSettings() {
    val intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", APPLICATION_ID, null)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}