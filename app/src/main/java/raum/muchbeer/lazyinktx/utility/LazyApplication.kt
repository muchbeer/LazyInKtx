package raum.muchbeer.lazyinktx.utility

import android.app.Application
import timber.log.Timber

class LazyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}