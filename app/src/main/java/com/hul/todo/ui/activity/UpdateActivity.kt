package com.hul.todo.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hul.todo.R
import com.hul.todo.databinding.ActivityUpdateBinding
import com.hul.todo.model.ToDoData
import com.hul.todo.utils.Constants.KEY_UPDATE
import com.hul.todo.utils.showAlertDialog
import com.hul.todo.utils.showShortToast
import com.hul.todo.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.activity_add_note.*

class UpdateActivity : AppCompatActivity() {
    private lateinit var mToDoViewModel: ToDoViewModel
    private lateinit var mBinding: ActivityUpdateBinding
    private lateinit var mBundle: Bundle
    private lateinit var toDoData: ToDoData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_update)
        mBinding.lifecycleOwner = this
        initToolbar()
        getData()

        mToDoViewModel = ViewModelProvider(this)[ToDoViewModel::class.java]

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_update, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            android.R.id.home -> {
                finish()
            }

            R.id.menu_save -> {

                updateDataToDb()
            }

            R.id.menu_delete -> {

                confirmItemRemoval()
            }
        }
        return true
    }

    private fun getData() {

        mBundle = intent.extras!!
        toDoData = mBundle.getParcelable(KEY_UPDATE)!!
        mBinding.data = toDoData
        Log.d("Hardik", "data: $toDoData")
    }

    private fun initToolbar() {

        supportActionBar!!.title = getString(R.string.update)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun confirmItemRemoval() {

        showAlertDialog(
            this,
            "Delete '${mBinding.data!!.title}'?",
            "Are you sure you want to remove '${mBinding.data!!.title}'?",
            getString(R.string.yes),
            { _, _ ->
                mToDoViewModel.deleteItem(toDoData)
                finish()
                showShortToast(this, "Successfully Removed: ${mBinding.data!!.title}")
            },
            getString(R.string.no),
            { _, _ -> }
        )
    }

    private fun updateDataToDb() {
        val mTitle = etTitle.text.toString()
        val mDescription = etDesc.text.toString()

        val validation = mToDoViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation) {
            // Update & re-insert Data to Database
            val newData = ToDoData(
                mBinding.data!!.id,
                mTitle,
                mDescription
            )
            mToDoViewModel.updateData(newData)
            finish()
            showShortToast(this, resources.getString(R.string.successfully_updated))

        } else {
            showShortToast(this, resources.getString(R.string.add_some_details))
        }
    }
}