package com.jayway.example.github

import android.app.Application
import com.facebook.stetho.Stetho


inline fun installStetho(application: Application) = Stetho.initializeWithDefaults(application)

