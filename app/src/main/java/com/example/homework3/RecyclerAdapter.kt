package com.example.homework3

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

const val SIZE_OF_BASKET = 5

class RecyclerAdapter(private var students: ArrayList<Student> , private val cellClickListener: CellClickListener): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() { //
    private var restoreList:Queue<Student> = LinkedList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student , parent , false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currItem = students[position]
        holder.textView.text = currItem.name + " " + currItem.id

        holder.itemView.setOnClickListener {

            cellClickListener.onCellClickListener(holder.itemView , currItem)
        }

        holder.deleteBtn.setOnClickListener {
            if(restoreList.size == SIZE_OF_BASKET){
                restoreList.poll()
                restoreList.add(students[position])

            }
            else{

                restoreList.add(students[position])
            }
            cellClickListener.restoreData(restoreList)
            students.removeAt(position)
            notifyDataSetChanged()
        }


    }


    override fun getItemCount(): Int {
        return students.size
    }
    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view)  {  //,View.OnClickListener

        val textView: TextView = view.findViewById(R.id.student_full_name)
        val deleteBtn: Button = view.findViewById(R.id.delete_btn)



        
    }
    interface CellClickListener{
        fun onCellClickListener(view : View , data: Student)
        fun restoreData(restoreList: Queue<Student>)

    }

}


