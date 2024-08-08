package uz.frame.player

import android.content.Context
import android.view.View
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory
import io.flutter.plugin.common.StandardMessageCodec

/** PlayerPlugin */
class PlayerPlugin: FlutterPlugin, MethodCallHandler {
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "PlayerPlugin")
    channel.setMethodCallHandler(this)
    flutterPluginBinding.platformViewRegistry.registerViewFactory("PlayerPlugin", PlayerViewFactory(flutterPluginBinding.applicationContext))
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}

class PlayerViewFactory(private val context: Context) : PlatformViewFactory(StandardMessageCodec.INSTANCE) {
  override fun create(context: Context, id: Int, args: Any?): PlatformView {
    return PlayerView(context)
  }
}

class PlayerView(context: Context) : PlatformView {
  private val view: View = View(context)

  override fun getView(): View {
    return view
  }

  override fun dispose() {}
}
