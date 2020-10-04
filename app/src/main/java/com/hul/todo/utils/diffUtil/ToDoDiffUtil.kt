package com.hul.todo.utils.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.hul.todo.model.ToDoData

class ToDoDiffUtil(
    private val mOldList: List<ToDoData>,
    private val mNewList: List<ToDoData>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList[oldItemPosition] === mNewList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList[oldItemPosition].id == mNewList[newItemPosition].id
                && mOldList[oldItemPosition].title == mNewList[newItemPosition].title
                && mOldList[oldItemPosition].description == mNewList[newItemPosition].description
    }
}