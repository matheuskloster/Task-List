package com.kloster.matheus.tasklist.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kloster.matheus.tasklist.databinding.ActivityDoneTasksBinding
import com.kloster.matheus.tasklist.model.DataStore
import com.kloster.matheus.tasklist.view.TaskAdapter

class DoneTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoneTasksBinding
    private lateinit var adapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoneTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DataStore.setContext(this)

        configureFabReturn()

        configureRcv()
    }

    fun configureFabReturn() {
        binding.fabReturn.setOnClickListener{
            Intent().apply {
                setResult(RESULT_OK, this)
            }
            finish()
        }
    }

    private fun configureRcv(){

        //conectando a recycle-view com o adapter
        LinearLayoutManager(this).apply {
            this.orientation = LinearLayoutManager.VERTICAL
            binding.rcvDoneTasks.layoutManager = this

            adapter = TaskAdapter(DataStore.tasks.filter { it -> it.status == "CONCLUÍDA" }.toMutableList()).apply { binding.rcvDoneTasks.adapter = this }
        }
    }


}