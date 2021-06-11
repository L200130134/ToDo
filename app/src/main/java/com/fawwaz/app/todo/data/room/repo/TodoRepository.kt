package com.fawwaz.app.todo.data.room.repo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fawwaz.app.todo.data.room.dao.TodoDao
import com.fawwaz.app.todo.data.room.db.ToDoDatabase
import com.fawwaz.app.todo.data.room.model.RequestToDoModel
import com.fawwaz.app.todo.data.room.model.ToDoModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TodoRepository(application: Application) : TodoDao {

    private val todoDao: TodoDao?
    val mutableLiveData : MutableLiveData<RequestToDoModel> = MutableLiveData()

    init {
        val database: ToDoDatabase = ToDoDatabase.getInstance(application)
        todoDao = database.todoDao()
    }

    override fun insert(model: ToDoModel) {
        GlobalScope.launch {
            todoDao?.insert(model)
        }
    }

    override fun insert(models: List<ToDoModel>) {
        GlobalScope.launch {
            todoDao?.insert(models)
        }
    }

    override fun update(model: ToDoModel) {
        GlobalScope.launch {
            todoDao?.update(model)
        }
    }

    override fun update(models: List<ToDoModel>) {
        GlobalScope.launch {
            todoDao?.update(models)
        }
    }

    override fun delete(model: ToDoModel) {
        GlobalScope.launch {
            todoDao?.delete(model)
        }
    }

    override fun delete(models: List<ToDoModel>) {
        GlobalScope.launch {
            todoDao?.delete(models)
        }
    }

    override fun delete(userId: String, dateId: String) {
        GlobalScope.launch {
            todoDao?.delete(userId, dateId)
        }
    }

    override fun deleteAll(userId: String) {
        GlobalScope.launch {
            todoDao?.deleteAll(userId)
        }
    }

    override fun getItemsCount(userId: String, dateId: String): Int {
        return todoDao!!.getItemsCount(userId, dateId)
    }

    override fun getItemsCount(userId: String): Int {
        return todoDao!!.getItemsCount(userId)
    }

    override fun getData(userId: String, dateId: String): MutableList<ToDoModel> {
        return todoDao!!.getData(userId, dateId)
    }

    override fun getLiveData(userId: String, dateId: String): LiveData<MutableList<ToDoModel>> {
        return todoDao!!.getLiveData(userId, dateId)
    }

    override fun getLiveData(userId: String): LiveData<MutableList<ToDoModel>> {
        return todoDao!!.getLiveData(userId)
    }

    fun requestWithMutableLiveData(userId: String, dateId: String) {
        GlobalScope.launch {
            val list = getData(userId, dateId)
            val rm = RequestToDoModel()
            rm.items = list
            rm.userId = userId
            rm.dateId = dateId
            mutableLiveData.postValue(rm)
        }
    }
}