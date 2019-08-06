package com.connect.android.client.model.messages

import java.io.Serializable

/**
 * Created by evgenii on 7/29/17.
 */
data class ChatData(val chatId: String?, val text: String?, val userId: String) : Serializable