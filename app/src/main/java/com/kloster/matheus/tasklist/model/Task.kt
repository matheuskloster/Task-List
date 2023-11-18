package com.kloster.matheus.tasklist.model

private const val TO_DO = "A FAZER"

class Task(name:String, status:String) {
    constructor(name: String): this(name, TO_DO)

}