package com.example.todolist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import com.example.todolist.di.ApplicationScope
import java.util.*

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
            var defaultTaskEn = "add some tasks here"
            var defaultTaskFa = "سلام"


            applicationScope.launch {
                dao.insert(Task(if(Locale.getDefault().language.equals("fa")){
                    defaultTaskFa}
                else defaultTaskEn, important = true))
            }
        }
    }
}