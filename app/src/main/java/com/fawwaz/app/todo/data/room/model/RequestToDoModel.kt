package com.fawwaz.app.todo.data.room.model

class RequestToDoModel {
    var dateId : String? = null
    var userId : String? = null
    var items : MutableList<ToDoModel> = mutableListOf()
}