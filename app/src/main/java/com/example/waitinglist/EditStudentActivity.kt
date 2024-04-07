package com.example.waitinglist

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        val btnmain : Button = findViewById<Button>(R.id.btnmain)


        val current_intent = intent

        val dbHandler = DBHandler(this);

        val txtname  : EditText =  findViewById<EditText>(R.id.txtedtname)
        val txtlast  : EditText =  findViewById<EditText>(R.id.txtedtlast)
        val txtprio   : TextView = findViewById<TextView>(R.id.txtedtpriority)

        val txtlayname : TextInputLayout = findViewById<TextInputLayout>(R.id.txtEdtInputName)
        val txtlaylast : TextInputLayout = findViewById<TextInputLayout>(R.id.txtedtInputLast)

        txtname.setText(current_intent.getStringExtra("student_name"))
        txtlast.setText(current_intent.getStringExtra("student_last"))
        txtprio.setText(current_intent.getStringExtra("student_priority"))

        val btndelete : Button = findViewById<Button>(R.id.btndltstudent)
        val btnupdate : Button = findViewById<Button>(R.id.btnupdstudent)
        val student_id : Int = current_intent.getIntExtra("student_id",0)


        //*****************************************************************
        //   CONFIRM DELETE
        //*****************************************************************


        val builder = AlertDialog.Builder(this)

        builder.setTitle("Confirm Delete")
        builder.setMessage("Do you want to Delete this student from the waiting list?")


        val positiveButton = builder.setPositiveButton(android.R.string.yes) { dialog, which ->

            dbHandler.deletestudent(student_id)

            val intentback : Intent = Intent(this@EditStudentActivity,MainActivity::class.java)

            startActivity(intentback)


        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }


        //*****************************************************************
        //    DELETE STUDENT
        //*****************************************************************

        btndelete.setOnClickListener()
        {
            builder.show()
        }

        //*****************************************************************
        //    UPDATE STUDENT
        //    Validations
        //     First name is required
        //     Last Name is required
        //*****************************************************************

        btnupdate.setOnClickListener()
        {


            if (txtname.text.toString().isEmpty())
            {
                txtlayname.error ="First Name is required"
                return@setOnClickListener
            }
            else
            {
                txtlayname.error=null;
            }

            if (txtlast.text.toString().isEmpty())
            {
                txtlaylast.error ="Last Name is required"
                return@setOnClickListener
            }
            else
            {
                txtlaylast.error=null;
            }

            dbHandler.updatestudent(student_id,txtname.text.toString(),txtlast.text.toString())
            val intentback : Intent = Intent(this@EditStudentActivity,MainActivity::class.java)

            startActivity(intentback)
        }

        btnmain.setOnClickListener()
        {
            val intentback : Intent = Intent(this@EditStudentActivity,MainActivity::class.java)

            startActivity(intentback)
        }

    }
}