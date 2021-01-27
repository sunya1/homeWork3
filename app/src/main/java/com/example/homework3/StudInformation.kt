package com.example.homework3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.homework3.databinding.StudInfoBinding

class StudInformation : Fragment() {
    private lateinit var binding: StudInfoBinding
    private lateinit var idText: TextView
//    private lateinit var avatarView: ImageView
    private lateinit var nameText: TextView
    private lateinit var surnameText: TextView
    private lateinit var gradeText: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.stud_info , container , false)
        val studentInfo = requireArguments().getParcelable<Student>("Student")

            binding.idView.text = studentInfo?.id.toString()
            binding.gradeView.text = studentInfo?.grade.toString().substring(0 , 3)
            binding.nameView.text = studentInfo?.name
            binding.surnameView.text = studentInfo?.surname




        return binding.root
    }
}