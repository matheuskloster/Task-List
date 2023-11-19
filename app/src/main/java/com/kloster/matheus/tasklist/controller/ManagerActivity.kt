package com.kloster.matheus.tasklist.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import com.kloster.matheus.tasklist.R
import com.kloster.matheus.tasklist.databinding.ActivityManagerBinding
import com.kloster.matheus.tasklist.model.DataStore
import com.kloster.matheus.tasklist.model.Task

class ManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManagerBinding
    private var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        intent.run {
            position = this.getIntExtra("position", -1)
            if (position > -1) {
                DataStore.getTask(position).apply {
                    binding.txtName.setText( this.name.toString())
                }
            }
        }

        binding.btnSalvar.setOnClickListener {
            val name = binding.txtName.text.toString()
            if (isAddTask()) {
                addTask(name)
            } else {
                editTask(name)
            }
            finish()
        }
    }

    private fun isAddTask(): Boolean {
        return position == -1
    }

    private fun addTask(name: String) {
        //validar se o nome está vazio ou null
        DataStore.tasks.add(Task(name))
        Intent().apply {
            this.putExtra("task", name)
            setResult(RESULT_OK, this)
        }
    }

    private fun editTask(name: String) {
        DataStore.editTask(position, Task(name))
        Intent().apply {
            this.putExtra("task", name)
            setResult(RESULT_OK, this)
        }

    }
}