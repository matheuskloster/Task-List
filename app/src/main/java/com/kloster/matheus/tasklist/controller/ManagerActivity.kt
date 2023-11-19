package com.kloster.matheus.tasklist.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kloster.matheus.tasklist.databinding.ActivityManagerBinding
import com.kloster.matheus.tasklist.model.DataStore
import com.kloster.matheus.tasklist.model.Task

class ManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManagerBinding
    private var position = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnCancelar.setOnClickListener {
            finish()
        }

        intent.run {
            if (isAddTask()) {
                binding.btnConcluirTarefa.visibility = View.INVISIBLE
            }
            position = this.getIntExtra("position", -1)
            if (position > -1) {
                binding.btnConcluirTarefa.visibility = View.VISIBLE
                DataStore.getTask(position).apply {
                    binding.txtName.setText( this.name )
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

        binding.btnConcluirTarefa.setOnClickListener {
            val name = binding.txtName.text.toString()
            completeTask(name)
            Intent().apply {
                this.putExtra("task", name)
                this.putExtra("comando", "concluir")
                setResult(RESULT_OK, this)
            }
            finish()
        }
    }

    private fun isAddTask(): Boolean {
        return position == -1
    }

    private fun addTask(name: String) {
        DataStore.addTask(Task(name))
        Intent().apply {
            this.putExtra("task", name)
            setResult(RESULT_OK, this)
        }
    }

    private fun completeTask(name: String) {
        DataStore.editTask(position, Task(name, "CONCLUÍDA"))

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