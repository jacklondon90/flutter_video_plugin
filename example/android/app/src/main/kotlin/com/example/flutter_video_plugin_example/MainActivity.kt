package uz.frame.player
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        flutterEngine
            .platformViewsController
            .registry
            .registerViewFactory("uz.frame.player.PlayerPlugin", MyAndroidViewFactory())
    }
}

class MyAndroidViewFactory : PlatformViewFactory(StandardMessageCodec.INSTANCE) {
    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        return MyAndroidView(context)
    }
}

class MyAndroidView(context: Context) : PlatformView {
    private val textView: TextView = TextView(context)

    init {
        textView.text = "Hello from Android"
     }

    override fun getView(): View {
        return textView
    }

    override fun dispose() {}
}