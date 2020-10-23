package com.example.tasker.ui

class Task {

    companion object {
        fun createList(): Task = Task()
    }

    var uid: String? = null
    var user_id: String? = null
    var task: String? = null
    var due: String? = null
    var date_added: String? = null
    var date_completed: String? = null
    var priority: String? = null
    var completed: Boolean? = false

}