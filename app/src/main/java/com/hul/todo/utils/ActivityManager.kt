package com.hul.todo.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.hul.todo.R

object ActivityManager {

    /**
     * To start activity with animation
     */
    fun startActivityWithAnimation(a: Activity, c: Class<*>) {
        a.startActivity(Intent(a, c))
        a.overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }


    /**
     * Finish existing work and start new activity
     */
    fun startFreshActivity(a: Activity, c: Class<*>) {
        a.apply {
            startActivity(Intent(a, c))
            a.overridePendingTransition(R.anim.from_right, R.anim.to_left)
            finish()
        }

    }

    /**
     * To start new activity with passing data
     */
    fun startActivityWithBundle(activity: Activity, aClass: Class<*>, bundle: Bundle) {
        val intent = Intent(activity, aClass).putExtras(bundle)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.from_right, R.anim.to_left)

    }

    /**
     * To start activity for result with passing data
     */
    fun startActivityForResultWithBundle(
        activity: Activity,
        aClass: Class<*>,
        requestCode: Int,
        bundle: Bundle
    ) {
        val intent = Intent(activity, aClass)
        intent.putExtra("data", bundle)
        activity.startActivityForResult(intent, requestCode)
    }

    /**
     * To start new activity and clear background activity stack
     */
    fun startFreshActivityClearStack(activity: Activity, aClass: Class<*>) {
        val intent = Intent(
            activity,
            aClass
        ).apply { Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
        activity.startActivity(intent)
        activity.finish()
    }

    /**
     * Start Activity for result
     * */
    fun startActivityForResult(activity: Activity, aClass: Class<*>, requestCode: Int) {
        val intent = Intent(activity, aClass)
        activity.startActivityForResult(intent, requestCode)
    }

}