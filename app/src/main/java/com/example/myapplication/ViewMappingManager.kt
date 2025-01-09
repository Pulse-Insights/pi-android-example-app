package com.example.myapplication

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.InputStreamReader

data class ViewMappingConfig(
    @SerializedName("viewMappings")
    val viewMappings: List<ViewMapping>
)

data class ViewMapping(
    @SerializedName("className")
    val className: String,
    @SerializedName("analyticsName")
    val analyticsName: String,
    @SerializedName("surveyConfig")
    val surveyConfig: SurveyConfig
)

data class SurveyConfig(
    @SerializedName("enabled")
    val enabled: Boolean = true,
    @SerializedName("frequency")
    val frequency: Long = 0
)

object ViewMappingManager {
    private val viewConfigs = mutableMapOf<String, ViewConfig>()

    fun registerView(className: String, config: ViewConfig) {
        viewConfigs[className] = config
    }

    fun getViewConfig(className: String): ViewConfig? = viewConfigs[className]

    fun loadConfigurations(context: Context) {
        try {
            val inputStream = context.resources.openRawResource(R.raw.view_mappings)
            val reader = InputStreamReader(inputStream)
            val viewMappingConfig = Gson().fromJson(reader, ViewMappingConfig::class.java)

            viewMappingConfig.viewMappings.forEach { mapping ->
                viewConfigs[mapping.className] = ViewConfig(
                    analyticsName = mapping.analyticsName,
                    shouldTriggerSurvey = mapping.surveyConfig.enabled,
                    surveyFrequency = mapping.surveyConfig.frequency
                )
            }

            Log.d("ViewMappingManager", "Loaded ${viewConfigs.size} view configurations")
        } catch (e: Exception) {
            Log.e("ViewMappingManager", "Failed to load view mappings", e)
            // Load default configurations
            loadDefaultConfigurations()
        }
    }

    private fun loadDefaultConfigurations() {
        val defaultMappings = mapOf(
            "MainActivity" to "Main",
            "secondActivity" to "SecondActivity",
            "HomeFragment" to "Main.Home",
            "DashboardFragment" to "Main.Dashboard",
            "NotificationsFragment" to "Main.Notifications"
        )

        defaultMappings.forEach { (className, analyticsName) ->
            viewConfigs[className] = ViewConfig(
                analyticsName = analyticsName,
                shouldTriggerSurvey = true,
                surveyFrequency = 0
            )
        }
    }
}