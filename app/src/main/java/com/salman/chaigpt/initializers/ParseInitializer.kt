package com.salman.chaigpt.initializers

import android.content.Context
import androidx.startup.Initializer
import com.parse.Parse
import com.salman.chaigpt.R

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/23/2023.
 */
class ParseInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        val config = Parse.Configuration.Builder(context)
            .applicationId(context.getString(R.string.back4app_app_id))
            .clientKey(context.getString(R.string.back4app_client_key))
            .server(context.getString(R.string.back4app_server_url))
            .build()

        Parse.initialize(config)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}