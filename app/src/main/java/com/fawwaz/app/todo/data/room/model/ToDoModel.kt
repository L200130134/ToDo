package com.fawwaz.app.todo.data.room.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "todo")
data class ToDoModel(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") var id: Int = 0,
    @SerializedName("userId") var userId: String?,
    @SerializedName("dateId") var dateId: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("date") var date: Long,
    @SerializedName("created") var created: Long = System.currentTimeMillis(),
    @SerializedName("status") var status: Int = 0
) : Parcelable {

}