package com.kloster.matheus.tasklist.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

class Database(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "tasks.db"
        const val DATABASE_VERSION = 1

        const val DB_TABLE_TASKS = "tasks"
        const val DB_FIELD_ID = "id"
        const val DB_FIELD_NAME = "name"
        const val DB_FIELD_STATUS ="status"

        const val sqlCreateTableTasks = "CREATE TABLE IF NOT EXISTS $DB_TABLE_TASKS (" +
                "$DB_FIELD_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$DB_FIELD_NAME TEXT, " +
                "$DB_FIELD_STATUS TEXT);"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val db = db ?: return

        db.beginTransaction()
        try {
            db.execSQL(sqlCreateTableTasks)
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.d("TasksAppDB", e.localizedMessage)
        }
        finally {
            db.endTransaction()
        }
    }


    fun getAllTasks():MutableList<Task> {
        var tasks = mutableListOf<Task>()
        val db = readableDatabase
        val cursor = db.query(
            DB_TABLE_TASKS,
            null,
            null,
            null,
            null,
            null,
            DB_FIELD_STATUS
        )
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DB_FIELD_ID))
                val name = getString(getColumnIndexOrThrow(DB_FIELD_NAME))
                val status = getString(getColumnIndexOrThrow(DB_FIELD_STATUS))
                val task = Task(name, status)
                task.id = id
                tasks.add(task)
            }
        }
        return tasks
    }

    fun addTask(task: Task):Long {
        var id: Long = 0
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DB_FIELD_NAME, task.name)
            put(DB_FIELD_STATUS, task.status)
        }
        db.beginTransaction()
        try {
            id = db.insert(DB_TABLE_TASKS, null, values)
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.d("TasksAppDB", e.localizedMessage)
        }
        finally {
            db.endTransaction()
        }
        return id
    }

    fun editTask(task: Task):Int {
        var countReturnFromDb = 0
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DB_FIELD_NAME, task.name)
            put(DB_FIELD_STATUS, task.status)
        }

        val selection = "$DB_FIELD_ID = ?"
        val selectionArgs = arrayOf(task.id.toString())

        db.beginTransaction()

        try {
            countReturnFromDb = db.update(DB_TABLE_TASKS, values, selection, selectionArgs)
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.d("TasksAppDB", "Falha ao editar o registro no banco de dados")
        } finally {
            db.endTransaction()
        }

        return countReturnFromDb

    }

    fun removeTask(task: Task):Int {

        val db = writableDatabase

        val selection = "$DB_FIELD_ID = ?"
        val selectionArgs = arrayOf(task.id.toString())

      return db.delete(DB_TABLE_TASKS,selection, selectionArgs) //retorna a qtde de registros afetados

    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}