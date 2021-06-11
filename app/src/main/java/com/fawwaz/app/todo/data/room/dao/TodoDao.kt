package com.fawwaz.app.todo.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fawwaz.app.todo.data.room.model.ToDoModel

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: ToDoModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(models: List<ToDoModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(model: ToDoModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(models: List<ToDoModel>)

    @Delete
    fun delete(model: ToDoModel)

    @Delete
    fun delete(models: List<ToDoModel>)

    @Query("DELETE FROM todo WHERE userId=:userId AND dateId=:dateId")
    fun delete(userId : String, dateId: String)

    @Query("DELETE FROM todo WHERE userId=:userId")
    fun deleteAll(userId : String)

    @Query("SELECT COUNT(id) FROM todo WHERE userId=:userId AND dateId=:dateId")
    fun getItemsCount(userId : String, dateId: String): Int

    @Query("SELECT COUNT(id) FROM todo WHERE userId=:userId")
    fun getItemsCount(userId : String): Int

    @Query("SELECT * FROM todo WHERE userId=:userId AND dateId=:dateId")
    fun getData(userId : String, dateId: String): MutableList<ToDoModel>

    @Query("SELECT * FROM todo WHERE userId=:userId AND dateId=:dateId")
    fun getLiveData(userId : String, dateId: String): LiveData<MutableList<ToDoModel>>

    @Query("SELECT * FROM todo WHERE userId=:userId")
    fun getLiveData(userId : String): LiveData<MutableList<ToDoModel>>
}