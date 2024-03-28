package com.ubaya.studentapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.studentapp.model.Subject

class SubjectViewModel (application: Application): AndroidViewModel(application) {
    val subjectsLD = MutableLiveData<ArrayList<Subject>>()
    val subjectLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue:RequestQueue? = null

    fun refresh(){
        loadingLD.value = true
        subjectLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://icfubaya2023.com/subject"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                val sType = object : TypeToken<List<Subject>>() { }.type
                val result = Gson().fromJson<List<Subject>>(it, sType)
                subjectsLD.value = result as ArrayList<Subject>?
                loadingLD.value = false
                Log.d("showvoley", result.toString())
            },
            {
                Log.d("showvoley", it.toString())
                subjectLoadErrorLD.value = false
                loadingLD.value = false
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}