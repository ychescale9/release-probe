package reactivecircus.releaseprobe.watchlist

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class ScreenTestRunner : AndroidJUnitRunner() {

    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, WatchlistTestApp::class.java.name, context)
    }
}
