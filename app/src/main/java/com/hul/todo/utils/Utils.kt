package com.hul.todo.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import com.hul.todo.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

fun showShortToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showLongToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun showAlertDialog(
    mContext: Context,
    mTitle: String,
    mMessage: String,
    mPositiveText: String,
    mPositiveAction: (Any, Any) -> Unit,
    mNegativeText: String,
    mNegativeAction: (Any, Any) -> Unit
): MaterialDialogs? {
    try {
        MaterialAlertDialogBuilder(
            mContext,
            R.style.ThemeOverlay_MaterialComponents_Dialog_Alert
        )
            .setTitle(mTitle).setMessage(mMessage).setPositiveButton(mPositiveText, mPositiveAction)
            .setNegativeButton(mNegativeText, mNegativeAction)
            .setCancelable(false)
            .show()

    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return null
}
