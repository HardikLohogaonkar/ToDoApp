package com.hul.todo.model.database

object DatabaseQuery {

    const val TO_DO = "todo_table"
    const val GET_ALL_DATA = "SELECT * FROM $TO_DO  ORDER BY id ASC"
    const val GET_SEARCH_DATA = "SELECT * FROM $TO_DO WHERE title LIKE :string OR description LIKE :string"
    const val DELETE_ALL_DATA = "DELETE FROM $TO_DO"
}