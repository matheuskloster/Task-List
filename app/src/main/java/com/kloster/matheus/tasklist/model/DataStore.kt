package com.kloster.matheus.tasklist.model

object DataStore {

    val tasks: MutableList<Task> = arrayListOf();

    init {
        tasks.add(Task("Lavar a louça"))
        tasks.add(Task("Estender roupa"))
        tasks.add(Task("Tirar o lixo"))
    }

    fun getTask(position: Int): Task {
        return tasks[position]
    }

    fun editTask(position: Int, task: Task) {
        tasks.set(position, task)
    }

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun deleteTask(position: Int) {
        tasks.removeAt(position)
    }

}