package com.example.bigheart

object QualificationsModel {
    data class Result(val name: String, val children: List<Children>)
    data class Children(val name: String, val children: List<Children>)
}
