package com.hul.todo.interfaces

import com.hul.todo.model.ToDoData

interface IOnItemClickListener {

    fun onItemClick(position: Int,toDoData: ToDoData)
}