package com.example.tts

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var speakButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Assuming your layout file is named activity_main.xml
        tts = TextToSpeech(this, this)
        speakButton = findViewById(R.id.button) // Assuming your button has the id "button"

        // Set up click listener for the button
        speakButton.setOnClickListener {
            // Speak Nepali text when the button is clicked
            speakNepali("नमस्ते, कस्तो छ?")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale("ne", "NP")) // Nepali language

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Nepali language not supported", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "TTS Initialization failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakNepali(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tts.isSpeaking) {
            tts.stop()
        }
        tts.shutdown()
    }
}
