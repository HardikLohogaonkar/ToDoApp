package com.hul.todo.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.hul.todo.R
import com.hul.todo.databinding.ActivityListBinding
import com.hul.todo.interfaces.IOnItemClickListener
import com.hul.todo.model.ToDoData
import com.hul.todo.ui.adapter.ListActivityAdapter
import com.hul.todo.utils.ActivityManager
import com.hul.todo.utils.Constants.KEY_UPDATE
import com.hul.todo.utils.SwipeToDeleteCallback
import com.hul.todo.utils.showAlertDialog
import com.hul.todo.utils.showShortToast
import com.hul.todo.viewmodel.ToDoViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_list.*
import java.util.concurrent.TimeUnit

class ListActivity : AppCompatActivity(), View.OnClickListener, IOnItemClickListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private var mToDoList = ArrayList<ToDoData>()
    private lateinit var mToDoViewModel: ToDoViewModel
    private lateinit var mListAdapter: ListActivityAdapter
    private lateinit var mBinding: ActivityListBinding
    var isExitFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        mToDoViewModel = ViewModelProvider(this)[ToDoViewModel::class.java]
        mBinding.toDoViewModel = mToDoViewModel
        mBinding.lifecycleOwner = this
        initToDoListData()
        getAllData()

    }

    private fun getAllData() {
        mToDoViewModel.getAllData.observe(this, getDataObserver)

    }

    private val getDataObserver = Observer<List<ToDoData>> {
        mToDoViewModel.checkIfDatabaseEmpty(it)
        mListAdapter.setData(it)
        Log.d("Hardik", "Data: $it")
    }

    private fun initToDoListData() {
        fabAddData.setOnClickListener(this)
        mListAdapter = ListActivityAdapter(this, mToDoList)
        rvToDoList.adapter = mListAdapter
        rvToDoList.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        swipeToDelete(rvToDoList)
    }

    private fun swipeToDelete(rvToDoList: RecyclerView?) {

        val mSwipeToDelete = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val deletedItem = mListAdapter.mToDoList[viewHolder.adapterPosition]
                // Delete Item
                mToDoViewModel.deleteItem(deletedItem)
                mListAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                // Restore Deleted Item
                restoreDeletedData(viewHolder.itemView, deletedItem)

            }
        }
        val mItemTouchHelper = ItemTouchHelper(mSwipeToDelete)
        mItemTouchHelper.attachToRecyclerView(rvToDoList)
    }

    private fun restoreDeletedData(view: View, deletedItem: ToDoData) {
        val snackBar = Snackbar.make(
            view, "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        ).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
        snackBar.setAction(getString(R.string.undo)) {
            mToDoViewModel.insertData(deletedItem)
        }
        snackBar.show()
    }

    override fun onClick(v: View?) {

        when (v!!.id) {

            R.id.fabAddData -> {

                ActivityManager.startActivityWithAnimation(this, AddNoteActivity::class.java)
            }
        }
    }

    override fun onItemClick(position: Int, toDoData: ToDoData) {

        val bundle = Bundle()
        bundle.putParcelable(KEY_UPDATE, toDoData)
        ActivityManager.startActivityWithBundle(this, UpdateActivity::class.java, bundle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_delete_all -> confirmRemoval()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_search, menu)
        val mSearch = menu?.findItem(R.id.menu_search)
        val mSearchView = mSearch?.actionView as androidx.appcompat.widget.SearchView
        mSearchView.isSubmitButtonEnabled = true
        mSearchView.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {

        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {

        val searchQuery = "%$query%"

        mToDoViewModel.searchDatabase(searchQuery).observe(this, searchObserver)
    }

    private val searchObserver = Observer<List<ToDoData>> {
        mListAdapter.setData(it)
    }

    private fun confirmRemoval() {

        showAlertDialog(
            this,
            getString(R.string.delete_everything),
            getString(R.string.delete_everything_msg),
            getString(R.string.yes),
            { _, _ ->
                mToDoViewModel.deleteAll()
                showShortToast(this, getString(R.string.successfully_removed_everything))
            },
            getString(R.string.no),
            { _, _ -> }
        )
    }

    @SuppressLint("CheckResult")
    override fun onBackPressed() {

        if (isExitFlag) {
            super.onBackPressed()
            return
        }
        isExitFlag = true
        showShortToast(this, getString(R.string.app_exit_msg))
        Completable.timer(2000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribe {
            isExitFlag = false
        }
    }
}