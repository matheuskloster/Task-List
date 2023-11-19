package com.kloster.matheus.tasklist.controller

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.google.android.material.snackbar.Snackbar
import com.kloster.matheus.tasklist.databinding.ActivityMainBinding
import com.kloster.matheus.tasklist.model.DataStore
import com.kloster.matheus.tasklist.view.TaskAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter
    private lateinit var gesture: GestureDetector


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DataStore.setContext(this)
        configureRcv()
        configureFab()
        configureFabTasksCompletadas()
        configureGesture()
        configureRecycleViewEvents()


    }


    private fun configureRcv(){
        //conectando a recycle-view com o adapter
        LinearLayoutManager(this).apply {
            this.orientation = LinearLayoutManager.VERTICAL
            binding.rcvTasks.layoutManager = this
            adapter = TaskAdapter(DataStore.tasks).apply { binding.rcvTasks.adapter = this }
        }
    }

    private fun configureFab() {
        binding.fab.setOnClickListener {
            Intent(this@MainActivity, ManagerActivity::class.java).apply {
                addTaskForResult.launch(this)
            }
        }
    }

    private fun configureFabTasksCompletadas() {
        binding.fabMenu.setOnClickListener {
            Intent(this@MainActivity, DoneTasksActivity::class.java).apply {
                loadDoneTasksActivityForResult.launch(this)
            }
        }
    }

    private fun configureGesture() {
        gesture = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {

                binding.rcvTasks.findChildViewUnder(e.x, e.y).run {
                    this?.let { view ->
                        val position = binding.rcvTasks.getChildAdapterPosition(view)
                        checkForTaskStatusAndEdit(position)
                    }
                     }
                 return super.onSingleTapConfirmed(e)
             }

            private fun checkForTaskStatusAndEdit(position: Int) {
                val selectedTask = DataStore.getTask(position)
                if (selectedTask.status == "CONCLUÍDA") {
                    showSnackMessage("A tarefa já está concluída, não pode ser modificada")
                } else {
                    Intent(this@MainActivity, ManagerActivity::class.java).run {
                        putExtra("position", position)
                        editTaskForResult.launch(this)
                    }
                }
            }

            override fun onLongPress(e: MotionEvent) {
                 super.onLongPress(e)

                 binding.rcvTasks.findChildViewUnder(e.x, e.y).run {
                     this?.let {view ->
                          binding.rcvTasks.getChildAdapterPosition(view).apply {
                             val task = DataStore.getTask(this)
                             AlertDialog.Builder(this@MainActivity).run {
                                 setMessage("Quer remover essa tarefa?")
                                 setPositiveButton("Excluir") { _,_ ->
                                     DataStore.deleteTask(this@apply)
                                     showSnackMessage("Tarefa ${task.name} excluída com sucesso")
                                     adapter.notifyDataSetChanged()
                                 }
                                 setNegativeButton("Cancelar", null)
                                 show()
                             }
                         }
                     }

                 }
             }
         })

    }

    private fun configureRecycleViewEvents() {
        binding.rcvTasks.addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                rv.findChildViewUnder(e.x, e.y).apply {
                    return this != null && gesture.onTouchEvent(e)
                }
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                TODO("Not yet implemented")
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun showSnackMessage(message: String) {
        Snackbar.make(
            this, binding.mainLayout, message, Snackbar.LENGTH_LONG
        ).show()
    }

    //Retorno da Activiy Manager
    private val addTaskForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { intent ->
                showSnackMessage("Tarefa ${intent.getStringExtra("task")} adicionada com sucesso!")
            }
            adapter.notifyDataSetChanged();
        } else {
            showSnackMessage("Operação cancelada com sucesso!")
        }

    }

    private val editTaskForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { intent ->
                if (intent.getStringExtra("comando").equals("concluir")) {
                    showSnackMessage("Tarefa ${intent.getStringExtra("task")} CONCLUIDA com sucesso!")
                } else {
                    showSnackMessage("Tarefa ${intent.getStringExtra("task")} alterada com sucesso!")
                }

            }
            adapter.notifyDataSetChanged();
        } else {
            showSnackMessage("Edição cancelada com sucesso!")
        }

    }

    private val loadDoneTasksActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { intent ->
                }
                showSnackMessage("Notificando que o data set mudou")
                adapter.notifyDataSetChanged();
            } else {
                adapter.notifyDataSetChanged()
            }
        }

}
