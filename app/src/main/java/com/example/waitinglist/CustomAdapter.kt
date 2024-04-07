package com.example.waitinglist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class CustomAdapter(context: Context, datalist: ArrayList<ListData?>?):
ArrayAdapter<ListData?>(context,R.layout.list_row,datalist!!){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view :View?
            view    = convertView
        val listData = getItem(position)


        if (view ==null)
        {
           view = LayoutInflater.from(context).inflate(R.layout.list_row,parent,false)
        }

        var listname = view?.findViewById<TextView>(R.id.nameTextViewID)
        var priority = view?.findViewById<TextView>(R.id.namepriority)
        var imgview  = view?.findViewById<ImageView>(R.id.image_delete)

        //imgview?.setImageResource(R.drawable.number_one)


        val letter: String = listData?.priority.toString().substring(0, 1)

        when (letter)
        {
            "G"-> imgview?.setImageResource(R.drawable.letter_g)
            "F"-> imgview?.setImageResource(R.drawable.number_one)
            "2"-> imgview?.setImageResource(R.drawable.number_two)
            "3"-> imgview?.setImageResource(R.drawable.number_thr)
            "4"-> imgview?.setImageResource(R.drawable.number_fou)
        }

        listname?.text = listData?.name +" "+listData?.last
        priority?.text = "Priority: " + listData?.priority

        return view as View
    }


}