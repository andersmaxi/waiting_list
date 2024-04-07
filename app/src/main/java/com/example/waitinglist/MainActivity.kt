package com.example.waitinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listAdapter: ArrayAdapter<*>

        val waitinglist : ListView = findViewById<ListView>(R.id.waitinglist)

        val btnadd      : View = findViewById<View>(R.id.btnadd)
        val btndelete     : Button = findViewById<Button>(R.id.btndelete)



        //*****************************************************************
        //    Database Instance
        //*****************************************************************
        val dbHandler = DBHandler(this);


        btnadd.setOnClickListener()
        {
            val intentadd : Intent = Intent(this@MainActivity,AddStudentActivity::class.java)

            startActivity(intentadd)
        }

        btndelete.setOnClickListener()
        {
            dbHandler.deleterecords()
        }


        //*****************************************************************
        //    Populate adapter with the students found tin the database
        //*****************************************************************

        var students = dbHandler.readStudents()

        listAdapter   = CustomAdapter(this,students)

        waitinglist.adapter = listAdapter

        waitinglist.setOnItemClickListener { parent, view, position, id ->


           val editstudent : Intent = Intent (this@MainActivity,EditStudentActivity::class.java)

            //*****************************************************************
            //   PAss parameters to edit activity
            //*****************************************************************
            editstudent.putExtra("student_name",students[position]?.name)
            editstudent.putExtra("student_last",students[position]?.last)
            editstudent.putExtra("student_priority",students[position]?.priority)
            editstudent.putExtra("student_id",students[position]?.id)

            startActivity(editstudent)

        }


    }
}