package com.sporticast.Helper

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object TextToSpeechHelper {
    private var mediaPlayer: MediaPlayer? = null

    fun speakWithFPT(context: Context, text: String, onComplete: () -> Unit = {}) {
        val apiKey = "Ps4NHRxyjx3HJRIfbNE9QNzw30Gce9Q1"
        val voice = "banmai"

        thread {
            try {
                val url = URL("https://api.fpt.ai/hmi/tts/v5")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("api-key", apiKey)
                conn.setRequestProperty("voice", voice)
                conn.setRequestProperty("Content-Type", "text/plain")
                conn.connectTimeout = 10000
                conn.readTimeout = 10000
                conn.doOutput = true
                conn.outputStream.use { it.write(text.toByteArray(Charsets.UTF_8)) }

                if (conn.responseCode == 200) {
                    val response = conn.inputStream.bufferedReader().use { it.readText() }
                    val audioUrl = JSONObject(response).getString("async")

                    val audioConn = URL(audioUrl).openConnection() as HttpURLConnection
                    val tempMp3 = File.createTempFile("tts_audio", ".mp3", context.cacheDir)
                    audioConn.inputStream.use { input ->
                        FileOutputStream(tempMp3).use { output ->
                            input.copyTo(output)
                        }
                    }

                    mediaPlayer?.release()
                    mediaPlayer = MediaPlayer().apply {
                        setDataSource(tempMp3.absolutePath)
                        setOnPreparedListener {
                            it.start()
                        }
                        setOnCompletionListener {
                            it.release()
                            mediaPlayer = null
                            onComplete()
                        }
                        prepareAsync()
                    }
                } else {
                    onComplete()
                }
            } catch (e: Exception) {
                onComplete()
            }
        }
    }
}
