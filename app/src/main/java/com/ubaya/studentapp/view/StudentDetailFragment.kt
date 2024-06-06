package com.ubaya.studentapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.ubaya.studentapp.R
import com.ubaya.studentapp.databinding.FragmentStudentDetailBinding
import com.ubaya.studentapp.databinding.FragmentStudentListBinding
import com.ubaya.studentapp.model.Student
import com.ubaya.studentapp.viewmodel.DetailViewModel
import com.ubaya.studentapp.viewmodel.ListViewModel
import com.ubaya.studentapp.viewmodel.SubjectViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class StudentDetailFragment : Fragment(), ButtonUpdateClickListener {
    private lateinit var binding:FragmentStudentDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.student = Student("", "", "", "", "https://randomuser.me/api/portraits/women/70.jpg")
        val studentId = StudentDetailFragmentArgs.fromBundle(requireArguments()).studentId

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch(studentId)

        viewModel.studentLD.observe(viewLifecycleOwner, Observer { student ->
            binding.student = student
            binding.listener = this
        })
    }

    override fun onButtonUpdateClick(v: View) {
        val student = binding.student
        if (student != null) {
            Observable.timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("Messages", "five seconds")
                    MainActivity.showNotification(
                        student.name.toString(),
                        "A new notification created",
                        R.drawable.baseline_person_24
                    )
                }
        }
    }
}