package ru.antontikhonov.android.timetableistu

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.antontikhonov.android.timetableistu.di.appModule
import ru.antontikhonov.android.timetableistu.di.networkModule
import ru.antontikhonov.android.timetableistu.di.timetableViewModelModule

class TimetableApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TimetableApplication)
            modules(
                appModule,
                networkModule,
                timetableViewModelModule,
            )
        }
    }
}
