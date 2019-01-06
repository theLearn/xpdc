package com.example.hongcheng.data.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
import android.content.Context


class DBInit private constructor() {

    object SingleHolder {
        val INSTANCE : DBInit = DBInit()
    }

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getInstance() : DBInit {
            return SingleHolder.INSTANCE
        }
    }

    fun init(context: Context) {
        // Reset the database to have new data on every run.
//        context.deleteDatabase(AppDatabase.DATABASE_NAME)
        //创建数据库
        appDatabase = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, AppDatabase.DATABASE_NAME)
                //                    .addCallback(object : RoomDatabase.Callback() {
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
////                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
////                            WorkManager.getInstance().enqueue(request)
//                        }
//                    })
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .build()
    }

    fun getAppDatabase(): AppDatabase? {
        if (appDatabase == null) throw NullPointerException("数据库连接未初始化")
        return appDatabase
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
        }
    }
}