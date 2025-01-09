package com.example.myapplication

import android.app.Application
import android.content.Context
import android.util.Log
import com.pulseinsights.surveysdk.ExtraConfig
import com.pulseinsights.surveysdk.PulseInsights
import java.lang.ref.WeakReference

object PulseInsightsManager {
    private lateinit var pulseInsights: PulseInsights
    private var currentContext: WeakReference<Context>? = null

    fun initialize(application: Application, apiKey: String, config: ExtraConfig) {
        pulseInsights = PulseInsights(application, apiKey, config)
        pulseInsights.setDebugMode(true)
    }

    fun setContext(context: Context) {
        currentContext = WeakReference(context)
        pulseInsights.context = context
    }

    fun triggerSurvey(viewName: String) {
        try {
            pulseInsights.apply {
                setViewName(viewName)
                serve()
            }
        } catch (e: Exception) {
            Log.e("PulseInsights", "Failed to trigger survey", e)
        }
    }
}