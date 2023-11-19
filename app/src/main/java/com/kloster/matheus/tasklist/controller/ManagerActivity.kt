package com.kloster.matheus.tasklist.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kloster.matheus.tasklist.R
import com.kloster.matheus.tasklist.databinding.ActivityManagerBinding
import com.kloster.matheus.tasklist.model.DataStore
import com.kloster.matheus.tasklist.model.Task

class ManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        binding.btnSalvar.setOnClickListener {
            val name = binding.txtName.text.toString()
            DataStore.tasks.add(Task(name))
            finish()
        }
    }
}