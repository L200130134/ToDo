package com.fawwaz.app.todo.data.room.repo

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fawwaz.app.todo.MainApp
import com.fawwaz.app.todo.data.room.dao.TodoDao
import com.fawwaz.app.todo.data.room.model.RequestToDoModel
import com.fawwaz.app.todo.data.room.model.ToDoModel

class TodoViewModel(application: Application) : ViewModel(), TodoDao {

    private val repository: TodoRepository = TodoRepository(application)

    override fun insert(model: ToDoModel) {
        repository.insert(model)
    }

    override fun insert(models: List<ToDoModel>) {
        repository.insert(models)
    }

    override fun update(model: ToDoModel) {
        repository.update(model)
    }

    override fun update(models: List<ToDoModel>) {
        repository.update(models)
    }

    override fun delete(model: ToDoModel) {
        repository.delete(model)
    }

    override fun delete(models: List<ToDoModel>) {
        repository.delete(models)
    }

    override fun delete(userId: String, dateId: String) {
        repository.delete(userId, dateId)
    }

    override fun deleteAll(userId: String) {
        repository.deleteAll(userId)
    }

    override fun getItemsCount(userId: String, dateId: String): Int {
        return repository.getItemsCount(userId, dateId)
    }

    override fun getItemsCount(userId: String): Int {
        return repository.getItemsCount(userId)
    }

    override fun getData(userId: String, dateId: String): MutableList<ToDoModel> {
        return repository.getData(userId, dateId)
    }

    override fun getLiveData(userId: String, dateId: String): LiveData<MutableList<ToDoModel>> {
        return repository.getLiveData(userId, dateId)
    }

    override fun getLiveData(userId: String): LiveData<MutableList<ToDoModel>> {
        return repository.getLiveData(userId)
    }

    fun requestWithMutableLiveData(userId: String, dateId: String) {
        return repository.requestWithMutableLiveData(userId, dateId)
    }

    fun getMutableLiveData() : MutableLiveData<RequestToDoModel> {
        return repository.mutableLiveData
    }

    class ToDoViewModelFactory(private val context: Context?): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val appContext = context?.applicationContext
            if (appContext is Application) {
                @Suppress("UNCHECKED_CAST")
                return TodoViewModel(appContext) as T
            }
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(MainApp.instance) as T
        }
    }
}