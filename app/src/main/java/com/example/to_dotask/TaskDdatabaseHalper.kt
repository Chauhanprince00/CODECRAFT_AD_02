package com.example.to_dotask

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TaskDdatabaseHalper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "task.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "alltasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun inserttask(task: task){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,task.title)
            put(COLUMN_CONTENT,task.content)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllTasks():List<task>{
        val tasklist = mutableListOf<task>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id  = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title  = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content  = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val task = task(id,title,content)
            tasklist.add(task)
        }
        cursor.close()
        db.close()
        return tasklist
    }
    fun updatetask(task: task){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,task.title)
            put(COLUMN_CONTENT,task.content)
        }
        val whereClaues = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(task.id.toString())
        db.update(TABLE_NAME,values,whereClaues,whereArgs)
        db.close()
    }

    fun gettaskById(taskID:Int):task{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $taskID"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return task(id,title,content)
    }

    fun deleteTask(taskID: Int){
        val db = writableDatabase
        val whereclous = "$COLUMN_ID = ?"
        val whereAegs = arrayOf(taskID.toString())
        db.delete(TABLE_NAME,whereclous,whereAegs)
        db.close()
    }
}