package uz.frame.player

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformViewRegistry

class MainActivity : FlutterActivity() {
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        val registry: PlatformViewRegistry = flutterEngine.platformViewsController.registry
        val messenger: BinaryMessenger = flutterEngine.dartExecutor.binaryMessenger
        registry.registerViewFactory("PlayerPlugin", PlayerViewFactory(messenger))
    }
}