package com.kloster.matheus.tasklist.model

import android.content.Context
import android.util.Log

object DataStore {

    var tasks: MutableList<Task> = arrayListOf()

    private var database: Database? = null


    fun setContext(context: Context) {
        database = Database(context)
        database?.let {db ->
            tasks = db.getAllTasks()
        }
    }


    fun getTask(position: Int): Task {
        return tasks[position]
    }

    fun editTask(position: Int, task: Task) {
        task.id = getTask(position).id
        val count = database?.editTask(task) ?: return
        if (count > 0) {
            tasks[position] = task
        }
    }

    fun addTask(task: Task) {
        val id = database?.addTask(task) ?: return

        if (id > 0) {
            task.id = id
            tasks.add(task)
        } else {
            Log.d("TasksAppDB", "Falha ao adicionar a tarefa, id retornado pelo DB com problema")
        }

    }

    fun deleteTask(position: Int) {
        val task = getTask(position)
        val count = database?.removeTask(task) ?: return
        if (count > 0) {
            tasks.removeAt(position)
        }
    }

}