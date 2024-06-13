
package com.example.onlybooking

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class OwnerActivity : AppCompatActivity() {

    private lateinit var buttons: List<Button>
    private val handler = Handler(Looper.getMainLooper())
    private val originalTextMap = mutableMapOf<Button, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner)

        buttons = listOf(
            findViewById(R.id.btn7to8),
            findViewById(R.id.btn8to9),
            findViewById(R.id.btn9to10),
            findViewById(R.id.btn10to11),
            findViewById(R.id.btn11to12),
            findViewById(R.id.btn12to1),
            findViewById(R.id.btn1to2),
            findViewById(R.id.btn2to3),
            findViewById(R.id.btn3to4),
            findViewById(R.id.btn4to5),
            findViewById(R.id.btn5to6),
            findViewById(R.id.btn6to7)
        )

        // Initialize buttons as invisible
        for (button in buttons) {
            button.visibility = Button.INVISIBLE
        }
    }

    private fun onButtonClick(button: Button) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Book or Hold?")
            .setPositiveButton("Book") { _, _ ->
                bookButton(button)
            }
            .setNegativeButton("Hold") { _, _ ->
                holdButton(button)
            }
            .setCancelable(false)
            .show()
    }

    private fun bookButton(button: Button) {
        // Store the original text
        originalTextMap[button] = button.text.toString()

        // Update the button state to "Booked"
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        button.text = "Booked ($currentTime)"
        button.isEnabled = false
        showToast("Button booked!")

        // Make the button visible
        button.visibility = Button.VISIBLE

        // Revert the button state after 30 seconds
        handler.postDelayed({
            restoreButtonState(button)
            showToast("Button available again")
        }, 30000)
    }

    private fun holdButton(button: Button) {
        // Store the original text
        originalTextMap[button] = button.text.toString()

        // Update the button state to "Hold"
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        button.text = "Hold ($currentTime)"
        button.isEnabled = false
        showToast("Button on hold for 30 seconds")

        // Make the button visible
        button.visibility = Button.VISIBLE

        // Revert the button state after 30 seconds
        handler.postDelayed({
            restoreButtonState(button)
            showToast("Button available again")
        }, 30000)
    }

    private fun restoreButtonState(button: Button) {
        // Restore the original text, enable the button, and make it visible
        button.text = originalTextMap[button]
        button.isEnabled = true
        button.visibility = Button.VISIBLE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
