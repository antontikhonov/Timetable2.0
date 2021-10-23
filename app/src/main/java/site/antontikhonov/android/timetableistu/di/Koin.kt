package site.antontikhonov.android.timetableistu.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import site.antontikhonov.android.timetableistu.data.*
import site.antontikhonov.android.timetableistu.ui.screen.theme.ThemeViewModel
import site.antontikhonov.android.timetableistu.ui.screen.timetable.TimetableViewModel

private const val URL = "https://antontikhonov.ru/timetable/"

val appModule = module {
    single {
        androidApplication()
    }
}

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TimetableIstuApi::class.java)
    }
}

val timetableViewModelModule = module {
    viewModel { TimetableViewModel(get()) }
    viewModel { ThemeViewModel(get()) }

    single<TimetableRepository> { TimetableRepositoryImpl(get()) }
    single<ThemeRepository> { ThemeRepositoryImpl(get()) }
}