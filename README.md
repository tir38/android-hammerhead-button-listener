# Hammerhead Button Listener


Listen for buttons on Hammerhead's Karoo 1 and Karoo 2 devices. Includes helper method to check for device type.


## Usage

Within an `Activity`, create a `HammerheadButtonListener` and pass through `onKeyDown`, `onKeyUp`, and `onKeyLongPressed` events:


```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var hammerheadButtonListener: HammerheadButtonListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hammerheadButtonListener = HammerheadButtonListener()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return hammerheadButtonListener.onKeyDown(event)
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        val button = hammerheadButtonListener.onKeyLongPress(event)
        when (button) {
            PREVIOUS -> TODO()
            NEXT -> if (get
            BACK -> TODO()
            CONFIRM -> TODO()
            null -> TODO()
        }
        return button != null
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        val button = hammerheadButtonListener.onKeyUp(event)
        when (button) {
            PREVIOUS -> TODO()
            NEXT -> TODO()
            BACK -> TODO()
            CONFIRM -> TODO()
            null -> TODO()
        }
        return button != null
    }
}
```

The library will automatically differentiate long and short presses. It will only return `Button` objects from `onKeyUp` for short presses, not for long presses. Long presses can be observed from `onKeyLongPress`.

Additionally you may wish to implement different behavior on Karoo 1 and Karoo 2. Device type can be checked with `hammerheadDeviceType()`

```
override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
    val button = hammerheadButtonListener.onKeyUp(event)
    when (button) {
        PREVIOUS -> TODO()
        NEXT -> {
            // maybe you want different behavior on K1 vs K2
            when (hammerheadDeviceType()) {
                HammerheadDeviceType.K1 -> TODO()
                HammerheadDeviceType.K2 -> TODO()
                HammerheadDeviceType.UNKNOWN -> TODO()
            }
        }
        BACK -> TODO()
        CONFIRM -> TODO()
        null -> TODO()
    }
    return button != null
}
```

## Setup

This library is not hosted on Artifactory. Instead copy `HammerheadButtonListener` and `HammerheadDevice` directly into your project.


## Sample

See this working with the provided `/sample` app.





