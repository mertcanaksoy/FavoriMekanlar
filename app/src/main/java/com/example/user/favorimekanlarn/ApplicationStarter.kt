package com.example.user.favorimekanlarn

import android.app.Application
import com.parse.Parse

class ApplicationStarter : Application() {

    override fun onCreate() {
        super.onCreate()

        Parse.initialize(Parse.Configuration.Builder(this)
                .applicationId("QhtfgmhIyeK4NQlRJAYDzMfXsXNf0pybDcVywoPw")
                .clientKey("HOfEhNPCr5dIsIE3Dhow6vswvQCUwD1ovy6gbQAv")
                .server("https://parseapi.back4app.com/")
                .build())
    }

}