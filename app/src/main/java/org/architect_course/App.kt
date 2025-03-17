package org.architect_course

import android.app.Application
import androidx.room.Room
import org.architect_course.model.database.MovieDatabase

class App : Application() {

    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            MovieDatabase::class.java, "movie-db"
        ).build()
    }
}