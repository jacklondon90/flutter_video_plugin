package com.example.flutter_video_plugin_example

import PlayerViewFactory
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

class MainActivity : FlutterActivity() {
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        println("Registering PlayerViewFactory")
        flutterEngine.platformViewsController.registry.registerViewFactory(
            "PlayerPlugin",
            PlayerViewFactory(flutterEngine.dartExecutor.binaryMessenger)
        )
    }
}
