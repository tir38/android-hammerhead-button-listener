package io.hammerhead.buttontest

import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.jasonatwood.android.hammerhead.HammerheadButtonListener
import io.jasonatwood.android.hammerhead.HammerheadButtonListener.Button.*
import io.jasonatwood.android.hammerhead.hammerheadDeviceType

class MainActivity : AppCompatActivity() {

    private lateinit var hammerheadButtonListener: HammerheadButtonListener
    private var outputTextview: TextView? = null
    private var modelTextview: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        outputTextview = findViewById(R.id.output_text_view)
        modelTextview = findViewById<TextView>(R.id.model_text_view).also {
            it.text = hammerheadDeviceType().toString()
        }
        hammerheadButtonListener = HammerheadButtonListener()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return hammerheadButtonListener.onKeyDown(event)
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        val result = hammerheadButtonListener.onKeyLongPress(event)
        val message = when (result) {
            PREVIOUS -> "Previous LONG"
            NEXT -> "Next LONG"
            BACK -> "Back LONG"
            CONFIRM -> "Confirm LONG"
            null -> ""
        }

        outputTextview?.text = message
        return result != null
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        val result = hammerheadButtonListener.onKeyUp(event)
        val message = when (result) {
            PREVIOUS -> "Previous SHORT"
            NEXT -> "Next SHORT"
            BACK -> "Back SHORT"
            CONFIRM -> "Confirm SHORT"
            null -> ""
        }
        outputTextview?.text = message
        return result != null
    }
}