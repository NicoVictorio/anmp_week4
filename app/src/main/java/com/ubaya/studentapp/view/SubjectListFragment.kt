package com.ubaya.studentapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.studentapp.databinding.FragmentSubjectListBinding
import com.ubaya.studentapp.viewmodel.ListViewModel
import com.ubaya.studentapp.viewmodel.SubjectViewModel

class SubjectListFragment : Fragment(){
    private lateinit var binding: FragmentSubjectListBinding
    private lateinit var viewModel: SubjectViewModel
    private val subjectListAdapter  = SubjectListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubjectListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)
        viewModel.refresh()
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = subjectListAdapter

        observeViewModel()

        binding.refreshLayout.setOnRefreshListener {
            binding.recView.visibility = View.GONE
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
    }
    fun observeViewModel() {
        viewModel.subjectsLD.observe(viewLifecycleOwner, Observer {
            subjectListAdapter.updateSubjectList(it)
        })
        viewModel.subjectLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.txtError2?.visibility = View.VISIBLE
            } else {
                binding.txtError2?.visibility = View.GONE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.recView.visibility = View.GONE
                binding.progressLoad2.visibility = View.VISIBLE
            } else {
                binding.recView.visibility = View.VISIBLE
                binding.progressLoad2.visibility = View.GONE
            }
        })
    }
}