package com.hul.todo.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hul.todo.app.ToDo.Companion.mInstance
import com.hul.todo.model.database.ToDoDatabase
import com.hul.todo.model.database.dao.ToDoDao
import com.hul.todo.model.ToDoData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ToDoRepository(private val toDoDao: ToDoDao) {

    private var mTaskData = MutableLiveData<List<ToDoData>>()

    fun getAllData(): MutableLiveData<List<ToDoData>> {

        var getData = ToDoDatabase.getDatabase(mInstance).todoDao().getAllData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mTaskData.value = it
                mTaskData.postValue(it)

            }, {
                Log.e("Hardik", "Error: " + it.message)
            })
        return mTaskData
    }

    fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

    fun updateData(toDoData: ToDoData) {
        toDoDao.updateData(toDoData)
    }

    fun deleteItem(toDoData: ToDoData) {
        toDoDao.deleteItem(toDoData)
    }

    fun deleteAll() {
        toDoDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>> {
        return toDoDao.searchDatabase(searchQuery)
    }

}