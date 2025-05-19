package com.sporticast.Helper

    import android.app.Activity
    import android.content.Intent
    import android.speech.RecognizerIntent
    import androidx.activity.result.ActivityResultLauncher

    object VoiceRecognitionHelper {
        fun startVoiceRecognition(activityLauncher: ActivityResultLauncher<Intent>) {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN") // Hoặc "en-US"
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Hãy nói nội dung bạn muốn tìm...")
            }
            activityLauncher.launch(intent)
        }
    }

