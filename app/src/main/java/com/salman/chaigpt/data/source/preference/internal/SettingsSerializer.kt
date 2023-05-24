package com.salman.chaigpt.data.source.preference.internal

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.salman.chaigpt.Settings
import java.io.InputStream
import java.io.OutputStream

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/24/2023.
 */
object SettingsSerializer : Serializer<Settings> {
    override val defaultValue: Settings
        get() = Settings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Settings {
        try {
            return Settings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Settings, output: OutputStream) {
        t.writeTo(output)
    }
}