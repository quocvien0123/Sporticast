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
    fun speakWithFPT(context: Context, text: String) {
        val apiKey = "Ps4NHRxyjx3HJRIfbNE9QNzw30Gce9Q1"
        val voice = "banmai"

        thread {
            try {
                Log.d("TTS", "Sending request to FPT.AI...")

                // Gửi POST request đến FPT.AI
                val url = URL("https://api.fpt.ai/hmi/tts/v5")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("api-key", apiKey)
                conn.setRequestProperty("voice", voice)
                conn.setRequestProperty("Content-Type", "text/plain")
                conn.doOutput = true
                conn.outputStream.use { it.write(text.toByteArray(Charsets.UTF_8)) }

                // Kiểm tra phản hồi
                if (conn.responseCode == 200) {
                    val response = conn.inputStream.bufferedReader().use { it.readText() }
                    Log.d("TTS", "FPT Response: $response")

                    // Parse JSON và lấy trường async
                    val jsonObject = JSONObject(response)
                    val audioUrl = jsonObject.getString("async")

                    Log.d("TTS", "Audio URL: $audioUrl")

                    // Tải file mp3 từ URL
                    val audioConn = URL(audioUrl).openConnection() as HttpURLConnection
                    val tempMp3 = File.createTempFile("tts_audio", ".mp3", context.cacheDir)
                    audioConn.inputStream.use { input ->
                        FileOutputStream(tempMp3).use { output ->
                            input.copyTo(output)
                        }
                    }

                    // Phát âm thanh
                    val mediaPlayer = MediaPlayer().apply {
                        setDataSource(tempMp3.absolutePath)
                        prepare()
                        setOnPreparedListener {
                            it.start()
                            Log.d("TTS", "Audio started playing")
                        }
                        setOnCompletionListener {
                            it.release()
                            Log.d("TTS", "Audio playback completed")
                        }
                    }
                } else {
                    val error = conn.errorStream?.bufferedReader()?.use { it.readText() }
                    Log.e("TTS", "API Error: $error")
                }
            } catch (e: Exception) {
                Log.e("TTS", "Exception: ${e.message}", e)
            }
        }
    }
}
