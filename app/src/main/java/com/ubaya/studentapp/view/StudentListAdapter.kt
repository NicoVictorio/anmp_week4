package com.ubaya.studentapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.studentapp.databinding.StudentListItemBinding
import com.ubaya.studentapp.model.Student
import java.lang.Exception

class StudentListAdapter(val studentList:ArrayList<Student>)
    :RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>(), ButtonDetailClickListener {
        class StudentViewHolder(var binding: StudentListItemBinding)
            :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = StudentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.binding.student = studentList[position]
        holder.binding.listener = this
    }

    fun updateStudentList(newStudentList: ArrayList<Student>) {
        studentList.clear()
        studentList.addAll(newStudentList)
        notifyDataSetChanged()
    }

    override fun onButtonDetailClick(v: View) {
        val id = v.tag.toString()
        val action = StudentListFragmentDirections.actionStudentDetailFragment(id)
        Navigation.findNavController(v).navigate(action)
    }
}
