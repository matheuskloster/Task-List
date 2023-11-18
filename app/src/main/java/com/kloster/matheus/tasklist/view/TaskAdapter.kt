package com.kloster.matheus.tasklist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kloster.matheus.tasklist.databinding.AdapterTaskBinding
import com.kloster.matheus.tasklist.model.Task

class TaskAdapter(var tasks: MutableList<Task>):RecyclerView.Adapter<TaskAdapter.TaskHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        AdapterTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
            return TaskHolder(this)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        tasks[position].apply {
            holder.binding.txtStatus.text = this.status
            holder.binding.txtName.text = this.name
        }
    }

    inner class TaskHolder(var binding: AdapterTaskBinding): RecyclerView.ViewHolder(binding.root)
    //Holder armazena os dados do layout, cada elemento cria um holder (representação de uma célula)
}