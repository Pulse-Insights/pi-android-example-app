package com.example.myapplication

data class ViewConfig(
    val analyticsName: String,
    val shouldTriggerSurvey: Boolean = true,
    val surveyFrequency: Long = 0 // 0 means only trigger once, > 0 means trigger every n times
)