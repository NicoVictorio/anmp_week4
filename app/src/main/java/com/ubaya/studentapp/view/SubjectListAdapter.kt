package com.ubaya.studentapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ubaya.studentapp.databinding.SubjectListItemBinding
import com.ubaya.studentapp.model.Student
import com.ubaya.studentapp.model.Subject

class SubjectListAdapter(val subjectList:ArrayList<Subject>)
    :RecyclerView.Adapter<SubjectListAdapter.SubjectViewHolder>(){
        class SubjectViewHolder(var binding:SubjectListItemBinding)
            :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = SubjectListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.binding.txtSubjectName.text = subjectList[position].name
        holder.binding.txtSubjectDescription.text = subjectList[position].description
        holder.binding.txtSubjectTopics.text = subjectList[position].topics?.joinToString(", ")

        val resources = subjectList[position].resources
        val textbooksText = resources?.textbooks?.joinToString(", ") ?: ""
        val onlineCoursesText = resources?.onlineCourses?.joinToString(", ") ?: ""

        val displayText = "Textbooks: $textbooksText\nOnline Courses: $onlineCoursesText"

        holder.binding.txtSubjectResources.text = displayText

        val url = subjectList[position].images
        val builder = Picasso.Builder(holder.binding.root.context)
        builder.listener { picasso, uri, exception ->
            exception.printStackTrace() }
        builder.build().load(url).into(holder.binding.imgView)
    }

    fun updateSubjectList(newSubjectList: ArrayList<Subject>) {
        subjectList.clear()
        subjectList.addAll(newSubjectList)
        notifyDataSetChanged()
    }
}