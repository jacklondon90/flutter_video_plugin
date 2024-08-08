/*package uz.frame.player

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

class MainActivity : FlutterActivity() {
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        println("Registering PlayerViewFactory")
        flutterEngine.platformViewsController.registry.registerViewFactory(
            "PlayerPlugin", // This should match the type you are trying to create
            ExoPlayerViewFactory(flutterEngine.dartExecutor.binaryMessenger)
        )
    }
}*/