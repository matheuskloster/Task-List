package com.kloster.matheus.tasklist.model

private const val TO_DO = "A FAZER"

class Task(var name:String,var status:String) {

    var id: Long = -1
    constructor(name: String): this(name, TO_DO)


}