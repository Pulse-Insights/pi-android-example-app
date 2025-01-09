package com.example.myapplication

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.pulseinsights.surveysdk.ExtraConfig
import com.pulseinsights.surveysdk.PulseInsights

class MyApp : Application() {

    companion object {
        private lateinit var instance: MyApp

        fun getInstance(): MyApp {
            return instance
        }
    }

    private lateinit var pi: PulseInsights


    override fun onCreate() {
        super.onCreate()

        // init PulseInsights
        val config = ExtraConfig().apply {
            automaticStart = false
            customData = mapOf(
                "name" to "tester",
                "type" to "worker",
                "age" to "12"
            )
        }

        PulseInsightsManager.initialize(this, "PI-71294199", config)

        // load view mappings
        ViewMappingManager.loadConfigurations(this)

        // register activity lifecycle callbacks
        registerActivityLifecycleCallbacks(PulseInsightsLifecycleCallback())
    }
}

