package com.hul.todo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hul.todo.model.database.DatabaseQuery.TO_DO
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TO_DO)
@Parcelize
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var description: String
) : Parcelable