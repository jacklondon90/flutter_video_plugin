package uz.frame.player

import uz.frame.player.PlayerViewFactory
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import android.util.Log

class MainActivity : FlutterActivity() {
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        Log.d("MainActivity", "Registering PlayerViewFactory in MainActivity of Main Project")
        flutterEngine.platformViewsController.registry.registerViewFactory(
            "PlayerPlugin",
            PlayerViewFactory(flutterEngine.dartExecutor.binaryMessenger)
        )
    }
}
