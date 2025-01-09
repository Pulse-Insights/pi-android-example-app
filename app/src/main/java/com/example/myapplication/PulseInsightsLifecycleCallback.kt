package com.example.myapplication

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class PulseInsightsLifecycleCallback : Application.ActivityLifecycleCallbacks {
    private var lastSurveyTriggerTime = 0L
    private val SURVEY_DEBOUNCE_TIME = 1000L

    private fun shouldTriggerSurvey(viewConfig: ViewConfig?): Boolean {
        if (viewConfig == null || !viewConfig.shouldTriggerSurvey) return false

        val currentTime = System.currentTimeMillis()
        return if (currentTime - lastSurveyTriggerTime > SURVEY_DEBOUNCE_TIME) {
            lastSurveyTriggerTime = currentTime
            true
        } else false
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        val viewConfig = ViewMappingManager.getViewConfig(activity.localClassName)
        PulseInsightsManager.setContext(activity)

        if (shouldTriggerSurvey(viewConfig)) {
            PulseInsightsManager.triggerSurvey(viewConfig!!.analyticsName)
        }

        handleFragmentLifecycle(activity)
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityResumed(p0: Activity) {
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(p0: Activity) {
    }

    private fun handleFragmentLifecycle(activity: Activity) {
        if (activity !is FragmentActivity) return

        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentViewCreated(
                    fm: FragmentManager,
                    fragment: Fragment,
                    view: View,
                    savedInstanceState: Bundle?
                ) {
//                    val isTopFragment = fm.fragments.lastOrNull() == fragment
                    val viewConfig = ViewMappingManager.getViewConfig(fragment.javaClass.simpleName)
                    Log.d("Pulseinsights-lifecyclecb", "fragment loading ${fragment.javaClass.simpleName}")
//                    if (isTopFragment && shouldTriggerSurvey(viewConfig)) {
                    if (shouldTriggerSurvey(viewConfig)){
                        Log.d("Pulseinsights-lifecyclecb", "fragment loading ${fragment.javaClass.simpleName} triggering serve")
                        PulseInsightsManager.triggerSurvey(viewConfig!!.analyticsName)
                    }
                }
            },
            true
        )
    }
}