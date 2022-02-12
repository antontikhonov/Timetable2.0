package ru.antontikhonov.android.timetableistu.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.antontikhonov.android.timetableistu.data.*
import ru.antontikhonov.android.timetableistu.ui.screen.groups.GroupsViewModel
import ru.antontikhonov.android.timetableistu.ui.screen.news.NewsViewModel
import ru.antontikhonov.android.timetableistu.ui.screen.theme.ThemeViewModel
import ru.antontikhonov.android.timetableistu.ui.screen.timetable.TimetableViewModel

private const val THEME_SHARED_PREFERENCES_NAME = "theme_preferences"
private const val BASE_URL = "https://antontikhonov.ru/timetable/"
private const val GSON_DATE_FORMAT = "dd.MM.yyyy hh:mm:ss"

val appModule = module {

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(
            THEME_SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }
}

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TimetableApi::class.java)
    }
}

private val gson: Gson = GsonBuilder()
    .setDateFormat(GSON_DATE_FORMAT)
    .setLenient()
    .create()

val timetableViewModelModule = module {
    viewModel { TimetableViewModel(get(), get()) }
    viewModel { ThemeViewModel(get()) }
    viewModel { NewsViewModel(get()) }
    viewModel { GroupsViewModel(get()) }

    single<TimetableRepository> { TimetableRepositoryImpl(get(), get()) }
    single<ThemeRepository> { ThemeRepositoryImpl(get()) }
    single<NewsRepository> { NewsRepositoryImpl(get()) }
    single<GroupsRepository> { GroupsRepositoryImpl(get()) }
    single<StartDateRepository> { StartDateRepositoryImpl(get()) }
}
