package uz.frame.player

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodChannel

class ExoPlayerViewPlugin: FlutterPlugin {

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        binding
            .platformViewRegistry
            .registerViewFactory("exoplayer_view", ExoPlayerViewFactory(binding.binaryMessenger))
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        // Clean up resources if necessary
    }
}
