package com.example.flutter_video_plugin_example
import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class ExoPlayerViewFactory(private val messenger: BinaryMessenger) : PlatformViewFactory(StandardMessageCodec.INSTANCE) {
    @OptIn(UnstableApi::class)
    override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
        println( "Creating PlayerPlugin with viewId:$viewId")
        return ExoPlayerPlugin(context!!, viewId, messenger)
    }
}