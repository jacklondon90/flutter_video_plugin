package com.example.flutter_video_plugin_example

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.platform.PlatformView

class PlayerPlugin(
    context: Context, 
    viewId: Int, 
    messenger: BinaryMessenger
) : PlatformView {

    private val playerContainer: FrameLayout = LayoutInflater.from(context)
        .inflate(R.layout.exoplayer_view, null) as FrameLayout

    private val player: ExoPlayer = ExoPlayer.Builder(context).build()

    init {
        val playerView: PlayerView = playerContainer.findViewById(R.id.exoplayer)
        playerView.player = player
    }

    override fun getView(): View {
        return playerContainer
    }

    override fun dispose() {
        player.release()
    }
}
