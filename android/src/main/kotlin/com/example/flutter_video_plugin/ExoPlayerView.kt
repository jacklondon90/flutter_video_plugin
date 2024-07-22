package uz.frame.player

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.ui.PlayerView
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView

class ExoPlayerView(context: Context, viewId: Int, messenger: BinaryMessenger) : PlatformView {

    private val playerContainer: FrameLayout = LayoutInflater.from(context).inflate(R.layout.exoplayer_view, null) as FrameLayout
    private val player: ExoPlayer
    private val methodChannel: MethodChannel
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval: Long = 1000 // Update interval in milliseconds
    private val trackSelector: DefaultTrackSelector

    init {
        val mediaSourceFactory = DefaultMediaSourceFactory(context)
        trackSelector = DefaultTrackSelector(context)

        player = ExoPlayer.Builder(context)
            .setMediaSourceFactory(mediaSourceFactory)
            .setTrackSelector(trackSelector)
            .build()
        val playerView: PlayerView = playerContainer.findViewById(R.id.player_view)
        playerView.player = player

        val mediaItem = MediaItem.Builder()
            .setUri("https://files.etibor.uz/media/the_beekeeper/master.m3u8")
            .build()
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        methodChannel = MethodChannel(messenger, "fluff_view_channel_$viewId")

        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    val duration = player.duration
                    methodChannel.invokeMethod("updateDuration", duration / 1000.0) // Send duration in seconds
                }
            }
        })

        methodChannel.setMethodCallHandler { call, result ->
            when (call.method) {
                "seekTo" -> {
                    val position = call.argument<Double>("value")
                    if (position != null) {
                        val seekPosition = (position * player.duration).toLong()
                        player.seekTo(seekPosition)
                        result.success(null)
                    } else {
                        result.error("INVALID_ARGUMENT", "Position is null", null)
                    }
                }
                "play" -> {
                    player.play()
                    result.success(true)
                }
                "pause" -> {
                    player.pause()
                    result.success(true)
                }
                "10+" -> seekBy(10)
                "10-" -> seekBy(-10)
                else -> result.notImplemented()
            }
        }
        startPeriodicUpdates()
    }

    private fun startPeriodicUpdates() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                val currentPosition = player.currentPosition.toFloat() / player.duration.toFloat()
                methodChannel.invokeMethod("updateSlider", currentPosition)
                handler.postDelayed(this, updateInterval)
            }
        }, updateInterval)
    }

    private fun seekBy(seconds: Long) {
        val currentPosition = player.currentPosition
        val newPosition = currentPosition + seconds * 1000
        player.seekTo(newPosition.coerceIn(0, player.duration))
    }

    override fun getView(): View {
        return playerContainer
    }

    override fun dispose() {
        player.release()
    }
}
