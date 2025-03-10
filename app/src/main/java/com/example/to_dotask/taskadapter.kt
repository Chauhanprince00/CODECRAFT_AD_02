package com.example.to_dotask

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.to_dotask.databinding.TaskItemBinding

class taskadapter(private var tasks:List<task>,var context: Context):RecyclerView.Adapter<taskadapter.viewHolder>(){

    private val db:TaskDdatabaseHalper = TaskDdatabaseHalper(context)

    inner   class viewHolder( var binding: TaskItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var binding = TaskItemBinding.inflate(LayoutInflater.from(context),parent,false)
return viewHolder(binding)
    }

    override fun getItemCount(): Int {
       return tasks.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.binding.title.text =tasks.get(position).title
        holder.binding.content.text =tasks.get(position).content
        holder.binding.edit.setOnClickListener {
            val intent = Intent(context,update_activity::class.java).apply {
                putExtra("task_id",tasks.get(position).id)
            }
            context.startActivity(intent)

        }
        holder.binding.delete.setOnClickListener {
            db.deleteTask(tasks.get(position).id)
            refreshData(db.getAllTasks())
            Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show()
        }
    }
    fun refreshData(newtasks:List<task>){
        tasks = newtasks
        notifyDataSetChanged()
    }
}