package com.hul.todo.app

import android.app.Application

class ToDo : Application() {

    companion object {

        lateinit var mInstance: ToDo
    }

    override fun onCreate() {
        super.onCreate()

        mInstance = this
    }
}