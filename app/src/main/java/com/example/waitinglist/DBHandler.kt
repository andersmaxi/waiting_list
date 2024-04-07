package com.example.waitinglist

import android.R.attr.value
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHandler  // creating a constructor for our database handler.
    (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    // below method is for creating a database by running a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + LASTNAME_COL + " TEXT,"
                + PRIORITY_COL + " TEXT"
                + ")")

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query)
    }

    // this method is use to add new course to our sqlite database.
    //*****************************************************************
    //    Add new  STUDENT
    //*****************************************************************
    fun addNewStudent(
        studentName: String?,
        studentLast: String?,
        studentPriority: String?
    ) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        val db = this.writableDatabase

        // on below line we are creating a
        // variable for content values.
        val values = ContentValues()

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, studentName)
        values.put(LASTNAME_COL, studentLast)
        values.put(PRIORITY_COL, studentPriority)

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values)

        // at last we are closing our
        // database after adding database.
        db.close()
    }

    fun deleterecords ()
    {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM " + TABLE_NAME)
        db.close()
    }

    fun deletestudent (studentid : Int)
    {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE ID=" + studentid.toString() )
        db.close()
    }

    //*****************************************************************
    //    Update STUDENT
    //*****************************************************************

    fun updatestudent (studentid : Int ,studentname:String , studentlast :String)
    {
        val db = this.writableDatabase
        db.execSQL("Update " + TABLE_NAME + " set  lastname ='" + studentlast+"' , firstname='"+ studentname+ "' WHERE ID=" + studentid.toString() )
        db.close()
    }

    //*****************************************************************
    //    Validate if  STUDENT already exists
    //*****************************************************************

    fun studentalreadyexists(studentname:String , studentlast :String) : Boolean
    {
        val db = this.writableDatabase
        val sql = "SELECT EXISTS (SELECT * FROM "+ TABLE_NAME +" WHERE firstname='" + studentname + "' and  lastname='" + studentlast + "' LIMIT 1)"
        val cursor: Cursor = db.rawQuery(sql, null)
        cursor.moveToFirst()
        // cursor.getInt(0) is 1 if column with value exists
        return if (cursor.getInt(0) == 1) {
            cursor.close()
            true
        } else {
            cursor.close()
            false
        }
    }


    // we have created a new method for reading all the courses.
    fun readStudents(): ArrayList<ListData?> {
        // on below line we are creating a
        // database for reading our database.
        val db = this.readableDatabase

        // on below line we are creating a cursor with query to read data from database.
        val cursorStudents = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        // on below line we are creating a new array list.
        val courseModalArrayList: ArrayList<ListData?> = ArrayList<ListData?>()

        // moving our cursor to first position.
        if (cursorStudents.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(
                    ListData(
                        cursorStudents.getString(1),
                        cursorStudents.getString(2),
                        cursorStudents.getString(3),
                        cursorStudents.getInt(0)
                    )
                )
            } while (cursorStudents.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorStudents.close()
        return courseModalArrayList
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    companion object {
        // creating a constant variables for our database.
        // below variable is for our database name.
        private const val DB_NAME = "mobiledb"

        // below int is our database version
        private const val DB_VERSION = 1

        // below variable is for our table name.
        private const val TABLE_NAME = "students"

        // below variable is for our id column.
        private const val ID_COL = "id"

        // below variable is for our course name column
        private const val NAME_COL = "firstname"

        // below variable id for our course duration column.
        private const val LASTNAME_COL = "lastname"

        // below variable for our course description column.
        private const val PRIORITY_COL = "priority"


    }
}