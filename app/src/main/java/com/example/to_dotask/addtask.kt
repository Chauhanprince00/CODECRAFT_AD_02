package com.example.to_dotask

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.to_dotask.databinding.ActivityAddtaskBinding

class addtask : AppCompatActivity() {
    private lateinit var binding: ActivityAddtaskBinding
    private lateinit var db:TaskDdatabaseHalper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddtaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = TaskDdatabaseHalper(this)
        
        
        binding.saveBtn.setOnClickListener { 
            val title = binding.titleEditText.text.toString()
            val content = binding.taskEditText.text.toString()

            if (title.isNullOrEmpty() || content.isNullOrEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }else{
                val task = task(0,title,content)
                db.inserttask(task)
                finish()
                Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show()
            }

        }
    }
}