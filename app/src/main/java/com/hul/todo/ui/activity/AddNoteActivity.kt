package com.hul.todo.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hul.todo.R
import com.hul.todo.databinding.ActivityAddNoteBinding
import com.hul.todo.model.ToDoData
import com.hul.todo.utils.showShortToast
import com.hul.todo.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {
    private lateinit var mToDoViewModel: ToDoViewModel
    private lateinit var mBinding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
        mBinding.lifecycleOwner = this
        mToDoViewModel = ViewModelProvider(this)[ToDoViewModel::class.java]
        initToolbar()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            android.R.id.home -> {
                finish()
            }

            R.id.menu_add -> {

                insertDataToDb()
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    private fun initToolbar() {

        supportActionBar!!.title = getString(R.string.add)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun insertDataToDb() {
        val mTitle = etTitle.text.toString()
        val mDescription = etDesc.text.toString()

        val validation = mToDoViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation) {
            // Insert Data to Database
            val newData = ToDoData(
                0,
                mTitle,
                mDescription
            )
            mToDoViewModel.insertData(newData)
            finish()
            showShortToast(this, resources.getString(R.string.successfully_added))

        } else {
            showShortToast(this, resources.getString(R.string.add_some_details))
        }
    }
}