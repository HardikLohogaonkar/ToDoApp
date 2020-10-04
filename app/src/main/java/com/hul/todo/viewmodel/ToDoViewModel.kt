package com.hul.todo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hul.todo.app.ToDo
import com.hul.todo.model.ToDoData
import com.hul.todo.model.database.ToDoDatabase
import com.hul.todo.model.repository.ToDoRepository

class ToDoViewModel : ViewModel() {

    // For checking empty database
    val mEmptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)
    private val mInstance = ToDo.mInstance
    private val mToDoDao = ToDoDatabase.getDatabase(mInstance).todoDao()

    private val mRepository: ToDoRepository
    val getAllData: LiveData<List<ToDoData>>


    fun checkIfDatabaseEmpty(toDoData: List<ToDoData>) {
        mEmptyDatabase.value = toDoData.isEmpty()
    }

    /*
   * To check whether fields are empty or not
   * */

    fun verifyDataFromUser(title: String, description: String): Boolean {
        return !(title.isEmpty() || description.isEmpty())
    }

    init {
        mRepository = ToDoRepository(mToDoDao)
        getAllData = mRepository.getAllData()
    }

    fun insertData(toDoData: ToDoData) {
        mRepository.insertData(toDoData)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>> {

        return mRepository.searchDatabase(searchQuery)
    }

    fun updateData(toDoData: ToDoData) {

        return mRepository.updateData(toDoData)
    }

    fun deleteItem(toDoData: ToDoData) {

        return mRepository.deleteItem(toDoData)
    }

    fun deleteAll() {

        return mRepository.deleteAll()
    }

}