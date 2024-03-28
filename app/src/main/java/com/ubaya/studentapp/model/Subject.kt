package com.ubaya.studentapp.model

data class Subject(
    var id:String?,
    val name:String?,
    val description:String?,
    val topics:List<String>?,
    val resources:SubjectResources?,
    val images:String?,
)

data class SubjectResources(
    val textbooks:List<String>?,
    val onlineCourses:List<String>?
)