package com.fawwaz.app.todo.data.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fawwaz.app.todo.data.room.dao.TodoDao
import com.fawwaz.app.todo.data.room.model.ToDoModel

@Database(entities = [ToDoModel::class], version = 1)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        private var instance: ToDoDatabase? = null
        @Synchronized
        fun getInstance(context: Context): ToDoDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    ToDoDatabase::class.java, "ToDoDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance!!
        }

        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}