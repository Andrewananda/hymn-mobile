package com.devstart.hymn.di

import android.content.Context
import com.devstart.hymn.MainActivity
import com.devstart.hymn.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context) : AppComponent
    }

    fun inject(activity: MainActivity)

}