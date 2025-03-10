package com.example.to_dotask

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.to_dotask.databinding.ActivityUpdateBinding

class update_activity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db:TaskDdatabaseHalper
    private var taskID:Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = TaskDdatabaseHalper(this)
        
        taskID = intent.getIntExtra("task_id",-1)
        if (taskID == -1){
            finish()
            return
        }
        val task = db.gettaskById(taskID)
        binding.updateTitleEditText.setText(task.title)
        binding.updateTaskEditText.setText(task.content)
        
        binding.updateSaveBtn.setOnClickListener { 
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateTaskEditText.text.toString()
            val updateTask = task(taskID,newTitle,newContent)
            
            db.updatetask(updateTask)
            finish()
            Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show()
        }
    }
}