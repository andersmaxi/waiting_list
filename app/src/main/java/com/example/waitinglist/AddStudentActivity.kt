package com.example.waitinglist


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout


class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        val users = arrayOf(
            "Graduate",
            "4th year",
            "3th year",
            "2nd year",
            "First year"
        )

        var lspriority ="Graduate"


        val spin: Spinner = findViewById<Spinner>(R.id.spinpriority)
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.adapter = adapter
        spin.onItemSelectedListener = object :
                   AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                //Toast.makeText(this@AddStudentActivity, users[position], Toast.LENGTH_SHORT).show()
                lspriority= users[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val btnadd : Button = findViewById<Button>(R.id.btnaddstudent)

        val txtname :TextView = findViewById<TextView>(R.id.txtname)
        val  txtlast :TextView  = findViewById<TextView>(R.id.txtlast)

        val txtinputname : TextInputLayout = findViewById<TextInputLayout>(R.id.txtInputName)
        val txtinputlast : TextInputLayout = findViewById<TextInputLayout>(R.id.txtInputLast)


        val dbHandler = DBHandler(this);

        //*****************************************************************
        //    Add new STUDENT
        //    validations
        //    first name is required
        //    second name is required
        //*****************************************************************
        btnadd.setOnClickListener()
        {

            if (txtname.text.toString().isEmpty())
            {
                txtinputname.error ="First Name is required"
                return@setOnClickListener
            }
            else
            {
                txtinputname.error=null;
            }

            if (txtlast.text.toString().isEmpty())
            {
                txtinputlast.error ="Last Name is required"
                return@setOnClickListener
            }
            else
            {
                txtinputlast.error=null;
            }


            val exists = dbHandler.studentalreadyexists(txtname.text.toString(),txtlast.text.toString()) ;

            if (exists)
            {
                txtinputlast.error ="Student already exists in the waiting list"
                return@setOnClickListener

            }
            else {
                txtinputlast.error=null;
            }



            dbHandler.addNewStudent(txtname.text.toString(),txtlast.text.toString(),lspriority)

            Toast.makeText(this, "Student has been added.", Toast.LENGTH_SHORT).show();

            val intentback : Intent = Intent(this@AddStudentActivity,MainActivity::class.java)

            startActivity(intentback)
        }
    }
}