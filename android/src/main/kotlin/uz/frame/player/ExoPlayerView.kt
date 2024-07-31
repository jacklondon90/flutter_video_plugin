// android/src/main/kotlin/uz/frame/player/ExoPlayerView.kt
package uz.frame.player

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView

class ExoPlayerView(context: Context, viewId: Int, messenger: BinaryMessenger) : PlatformView {
    private val playerContainer: FrameLayout = LayoutInflater.from(context).inflate(R.layout.exoplayer_view, null) as FrameLayout
    private val player: ExoPlayer = ExoPlayer.Builder(context).build()
    private val methodChannel: MethodChannel = MethodChannel(messenger, "uz.frame.player/player_$viewId")
    private val handler = Handler(Looper.getMainLooper())
    private var isSliderBeingDragged = false
    private val updateInterval: Long = 1000

    init {
        val playerView: PlayerView = playerContainer.findViewById(R.id.player_view)
        playerView.player = player
        val mediaItem = MediaItem.fromUri("https://files.etibor.uz/media/the_beekeeper/master.m3u8")
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_READY -> {
                        methodChannel.invokeMethod("updateDuration", player.duration / 1000.0)
                        updateAudioAndSubtitleOptions()
                    }
                    Player.STATE_BUFFERING -> methodChannel.invokeMethod("updateBuffer", player.bufferedPercentage.toDouble())
                    Player.STATE_ENDED, Player.STATE_IDLE -> methodChannel.invokeMethod("updateBuffer", 0.0)
                }
            }

            override fun onPlayerError(error: Exception) {
                Log.e("ExoPlayerView", "Player error: ${error.message}")
            }
        })

        methodChannel.setMethodCallHandler { call, result ->
            when (call.method) {
                "seekTo" -> {
                    val position = call.argument<Double>("value")
                    if (position != null) {
                        player.seekTo((position * player.duration).toLong())
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
                "10+" -> {
                    seekBy(10)
                    result.success(true)
                }
                "10-" -> {
                    seekBy(-10)
                    result.success(true)
                }
                "changeAudio" -> {
                    val language = call.argument<String>("language")
                    if (language != null) {
                        changeAudio(language)
                        result.success(true)
                    } else {
                        result.error("INVALID_ARGUMENT", "Language is null", null)
                    }
                }
                "changeSubtitle" -> {
                    val language = call.argument<String>("language")
                    if (language != null) {
                        changeSubtitle(language)
                        result.success(true)
                    } else {
                        result.error("INVALID_ARGUMENT", "Language is null", null)
                    }
                }
                "changeVideoQuality" -> {
                    val url = call.argument<String>("url")
                    if (url != null) {
                        changeVideoQuality(url)
                        result.success(true)
                    } else {
                        result.error("INVALID_ARGUMENT", "URL is null", null)
                    }
                }
                "setPlaybackSpeed" -> {
                    val speed = call.argument<Double>("speed")
                    if (speed != null) {
                        player.setPlaybackSpeed(speed.toFloat())
                        result.success(true)
                    } else {
                        result.error("INVALID_ARGUMENT", "Speed is null", null)
                    }
                }
                "isSliderBeingDragged" -> {
                    isSliderBeingDragged = call.argument<Boolean>("isDragging") ?: false
                    result.success(true)
                }
                else -> result.notImplemented()
            }
        }

        startPeriodicUpdates()
    }

    private fun startPeriodicUpdates() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (!isSliderBeingDragged) {
                    methodChannel.invokeMethod("updateSlider", player.currentPosition.toFloat() / player.duration.toFloat())
                }
                handler.postDelayed(this, updateInterval)
            }
        }, updateInterval)
    }

    private fun seekBy(seconds: Int) {
        player.seekTo((player.currentPosition + seconds * 1000).coerceAtMost(player.duration))
    }

    private fun changeAudio(language: String) {
        // Logic to change audio track by language
    }

    private fun changeSubtitle(language: String) {
        // Logic to change subtitle track by language
    }

    private fun changeVideoQuality(url: String) {
        // Logic to change video quality by URL
    }

    override fun getView(): View {
        return playerContainer
    }

    override fun dispose() {
        player.release()
    }
}
