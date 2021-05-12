package com.devstart.hymn

import android.app.Application
import com.devstart.hymn.di.AppComponent
import com.devstart.hymn.di.DaggerAppComponent

class Hymn: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}