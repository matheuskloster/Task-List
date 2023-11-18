package com.kloster.matheus.tasklist.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.kloster.matheus.tasklist.R
import com.kloster.matheus.tasklist.databinding.ActivityMainBinding
import com.kloster.matheus.tasklist.databinding.AdapterTaskBinding
import com.kloster.matheus.tasklist.model.DataStore
import com.kloster.matheus.tasklist.view.TaskAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //conectando a recycle-view com o adapter
        LinearLayoutManager(this).apply {
            this.orientation = LinearLayoutManager.VERTICAL
            binding.rcvTasks.layoutManager = this
            adapter = TaskAdapter(DataStore.tasks).apply { binding.rcvTasks.adapter = this }

        }
    }
}