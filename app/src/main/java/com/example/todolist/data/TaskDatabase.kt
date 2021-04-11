package com.example.todolist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import com.example.todolist.di.ApplicationScope

@Database(entities = [Task::class] , version = 1 )
abstract class TaskDatabase : RoomDatabase(){

    abstract fun taskDao() : TaskDao

    class Callback  @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope
        private val applicationScope : CoroutineScope

        ):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            //db operations
            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("wash the dishes"))
                dao.insert(Task("do the laundry"))
                dao.insert(Task("buy groceries", important = true))
                dao.insert(Task("prepare food",completed = true))
                dao.insert(Task("call mom"))
                dao.insert(Task("visit grandma",completed =  true))
                dao.insert(Task("repair bike"))
                dao.insert(Task("call Elon Musk"))
            }
        }
    }
}