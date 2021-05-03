package com.example.hymn

import android.app.Application
import com.example.hymn.di.AppComponent
import com.example.hymn.di.DaggerAppComponent

class Hymn: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}