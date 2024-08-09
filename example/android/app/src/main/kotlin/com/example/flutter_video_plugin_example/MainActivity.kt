package com.example.flutter_video_plugin_example
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        println("Registering PlayerViewFactory")
        flutterEngine.platformViewsController.registry.registerViewFactory(
            "uz.frame.player",
            PlayerViewFactory(flutterEngine.dartExecutor.binaryMessenger)
        )
    }
}
