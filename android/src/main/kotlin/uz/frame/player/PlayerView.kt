package uz.frame.player

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.media3.ui.PlayerView
import io.flutter.plugin.platform.PlatformView

class PlayerView(context: Context) : PlatformView {
    private val playerContainer: FrameLayout = LayoutInflater.from(context).inflate(R.layout.exoplayer_view, null) as FrameLayout

    init {
        val playerView: PlayerView = playerContainer.findViewById(R.id.exoplayer)
    }

    override fun getView(): View {
        return view
    }

    override fun dispose() {}
}