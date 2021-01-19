package io.hammerhead.buttontest

import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.jasonatwood.android.hammerhead.ButtonListener
import io.jasonatwood.android.hammerhead.ButtonListener.Button.*

class MainActivity : AppCompatActivity() {

    private lateinit var buttonListener: ButtonListener
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.output_text_view)
        buttonListener = ButtonListener()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return buttonListener.onKeyDown(event)
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        val result = buttonListener.onKeyLongPress(event)
        when (result) {
            PREVIOUS -> textView?.text = "Previous LONG"
            NEXT -> textView?.text = "Next LONG"
            BACK -> textView?.text = "Back LONG"
            CONFIRM -> textView?.text = "Confirm LONG"
            null -> textView?.text = ""
        }
        return result != null
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        val result = buttonListener.onKeyUp(event)
        when (result) {
            PREVIOUS -> textView?.text = "Previous SHORT"
            NEXT -> textView?.text = "Next SHORT"
            BACK -> textView?.text = "Back SHORT"
            CONFIRM -> textView?.text = "Confirm SHORT"
            null -> textView?.text = ""
        }
        return result != null
    }
}