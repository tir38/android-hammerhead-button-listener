# Hammerhead Button Listener


Listen for buttons on Hammerhead's Karoo 1 and Karoo 2 devices.


## Usage

Within an `Activity`, create a `ButtonListener` and pass through `onKeyDown`, `onKeyUp`, and `onKeyLongPressed` events:


```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var buttonListener: ButtonListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ...
        buttonListener = ButtonListener()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return buttonListener.onKeyDown(event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        val result = buttonListener.onKeyUp(event)
        when (result) {
            // TODO handle short button presses
        }
        return result != null
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        val result = buttonListener.onKeyLongPress(event)
        when (result) {
	       // TODO handle long button presses
        }
        return result != null
    }
}
```

The library will automatically differentiate long and short presses. It will only return `Button` objects from `onKeyUp` for short presses, not for long presses. Long presses can be observed from `onKeyLongPress`.

## Setup

This library is not hosted on Artifactory. Instead copy `ButtonListener` and `DeviceHelper` directly into your project.


## Sample

See this working with the provided `/sample` app.





