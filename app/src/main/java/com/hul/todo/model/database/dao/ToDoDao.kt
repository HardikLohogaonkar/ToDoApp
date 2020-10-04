package com.hul.todo.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hul.todo.model.ToDoData
import com.hul.todo.model.database.DatabaseQuery.DELETE_ALL_DATA
import com.hul.todo.model.database.DatabaseQuery.GET_ALL_DATA
import com.hul.todo.model.database.DatabaseQuery.GET_SEARCH_DATA
import io.reactivex.Flowable

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(toDoData: ToDoData)

    @Query(GET_ALL_DATA)
    fun getAllData(): Flowable<List<ToDoData>>

    @Update
    fun updateData(toDoData: ToDoData)

    @Delete
    fun deleteItem(toDoData: ToDoData)

    @Query(DELETE_ALL_DATA)
    fun deleteAll()

    @Query(GET_SEARCH_DATA)
    fun searchDatabase(string: String): LiveData<List<ToDoData>>

}