package com.hul.todo.utils

import android.content.Intent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.google.android.material.card.MaterialCardView
import com.hul.todo.model.ToDoData
import com.hul.todo.ui.activity.ListActivity
import com.hul.todo.ui.activity.UpdateActivity

class BindingAdapters {

    companion object{

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>){
            when(emptyDatabase.value){
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

    }
}

