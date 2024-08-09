package uz.frame.player

import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class PlayerPlugin : FlutterPlugin {
    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        flutterPluginBinding
            .platformViewRegistry
            .registerViewFactory("PlayerPlugin", MyAndroidViewFactory(flutterPluginBinding.binaryMessenger))
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {}

    private class MyAndroidViewFactory(private val messenger: BinaryMessenger) : PlatformViewFactory(StandardMessageCodec.INSTANCE) {
        override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
            return MyAndroidView(context)
        }
    }

    private class MyAndroidView(context: Context) : PlatformView {
        private val textView: TextView = TextView(context)

        init {
            textView.text = "Hello from Android"
            // Additional setup for your Android view can go here
        }

        override fun getView(): View {
            return textView
        }

        override fun dispose() {}
    }
}