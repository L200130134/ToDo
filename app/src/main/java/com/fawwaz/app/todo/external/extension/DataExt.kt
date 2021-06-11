package com.fawwaz.app.todo.external.helper

import com.fawwaz.app.todo.data.room.model.ToDoModel

fun ToDoModel.clone(): ToDoModel {
    val model = ToDoModel(
        0, this.userId, this.dateId, this.title, this.description,
        this.date, this.created, this.status
    )
    model.id = this.id
    return model
}