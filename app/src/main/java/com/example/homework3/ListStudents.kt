package com.example.homework3

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework3.databinding.StudentsFragmentBinding
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class ListStudents: Fragment() , RecyclerAdapter.CellClickListener {
    private lateinit var recycler_view: RecyclerView
    private var list: ArrayList<Student> = ArrayList()
    private var set: HashSet<String> = HashSet()
    private val random: Random = Random()
    private lateinit var binding: StudentsFragmentBinding
    private lateinit var bitmap: Bitmap

    private var size = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.students_fragment, container, false)


        for (i in 0 until size) {
            list.add(Student(id = i, grade = 4.0, name = "Students$i", image = "${base64Img()}"))
            set.add("Students$i Student")
        }
        binding = DataBindingUtil.inflate(inflater , R.layout.students_fragment , container , false)
        recycler_view =  binding.studentsView



        binding.addBtn.setOnClickListener {
            if (binding.fullName.text.isEmpty()) {
                Toast.makeText(context, "Please Fill Your Name And Surname", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var fullName = binding.fullName.text.toString().split(" ")
                val max = 4
                val min = 1

                val grade: Double = min + (max - min) * random.nextDouble()


                val newStudent = Student(size, fullName[0], fullName[1], grade, "${base64Img()}")
                set.add(fullName[0] + " " + fullName[1])

                if (set.size == size) {
                    Toast.makeText(
                        context,
                        "${newStudent.name + " " + newStudent.surname} is already in list",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    list.add(newStudent)
                    size++
                    recycler_view.adapter?.notifyDataSetChanged()


                    binding.fullName.text.clear()
                }

            }
        }


        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = RecyclerAdapter(list , this )
        recycler_view.setHasFixedSize(true)

        return binding.root


    }
    private fun base64Img(): String{
        var baos = ByteArrayOutputStream()
        bitmap = BitmapFactory.decodeResource(resources , R.drawable.avatar)
        bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , baos)
        var bytes = baos.toByteArray()
        var encodeImage = Base64.encodeToString(bytes , Base64.DEFAULT)


        return encodeImage

    }


    override fun onCellClickListener(view: View , data: Student) {
        Navigation.findNavController(view).navigate(R.id.action_listStudents_to_studInformation , bundleOf(Pair("Student" , data)))
    }

    override fun restoreData(restoreList: Queue<Student>) { //
        binding.restoreBtn.setOnClickListener {
            list.addAll(restoreList)
            restoreList.clear()

            recycler_view.adapter?.notifyDataSetChanged()
        }
    }
}
