package com.hul.todo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hul.todo.databinding.ItemToDoListBinding
import com.hul.todo.interfaces.IOnItemClickListener
import com.hul.todo.model.ToDoData
import com.hul.todo.utils.diffUtil.ToDoDiffUtil

class ListActivityAdapter(
    private val mIOnItemClickListener: IOnItemClickListener,
    var mToDoList: List<ToDoData>
) :
    RecyclerView.Adapter<ListActivityAdapter.ViewHolderToDoList>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderToDoList {

        val itemToDoListBinding =
            ItemToDoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolderToDoList(itemToDoListBinding)
    }

    override fun getItemCount() = mToDoList.size

    override fun onBindViewHolder(holder: ViewHolderToDoList, position: Int) {

        holder.itemToDoListBinding.toDoData = mToDoList[position]


        holder.itemView.setOnClickListener {

            mIOnItemClickListener.onItemClick(position, mToDoList[position])
        }
    }

    class ViewHolderToDoList(val itemToDoListBinding: ItemToDoListBinding) :
        RecyclerView.ViewHolder(itemToDoListBinding.root) {

    }

    fun setData(toDoData: List<ToDoData>) {
        val toDoDiffUtil = ToDoDiffUtil(mToDoList, toDoData)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.mToDoList = toDoData
        toDoDiffResult.dispatchUpdatesTo(this)
    }
}