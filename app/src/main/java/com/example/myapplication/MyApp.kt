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
//    private val viewMap = hashMapOf(
//        "MainActivity" to "Main",
//        "secondActivity" to "SecondActivity",
//        "HomeFragment" to "Main.Home",
//        "DashboardFragment" to "Main.Dashboard",
//        "NotificationsFragment" to "Main.Notifications"
//    )


//    override fun onCreate(){
//        super.onCreate()
//        instance = this
//        //instantiate PulseInsights
//        getPulseInsights()
//
//        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
//            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?){
//                MyApp.getInstance().getPulseInsights().context = activity
//
//                //use viewMapping to authorize serve
//                if(viewMap.containsKey(activity.localClassName)){
//                    MyApp.getInstance().getPulseInsights().setViewName(viewMap[activity.localClassName])
//                    getInstance().getPulseInsights().serve()
//                }
////hardcoded crap
////                if(activity is secondActivity){
//////                    val pi = getPulseInsights()
//////                    pi.setContext(activity)
//////
//////
////                    Log.d("mylogs", "setting view: ${activity.localClassName}")
////                    myApp.getInstance().getPulseInsights().setViewName(activity.localClassName)
////                    getInstance().getPulseInsights().serve()
////
////
////                }
////                else{
////                    myApp.getInstance().getPulseInsights().setViewName(activity.localClassName)
////                    Log.d("mylogs", "setting view: ${activity.localClassName}")
////                }
//
//                //register fragment lifecycle manager
//                if(activity is FragmentActivity){
//                    activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
//                        object : FragmentManager.FragmentLifecycleCallbacks(){
//                            override fun onFragmentViewCreated(
//                                fm: FragmentManager,
//                                f: Fragment,
//                                v: View,
//                                savedInstanceState: Bundle?
//                            ) {
//                                super.onFragmentViewCreated(fm, f, v, savedInstanceState)
//                                Log.d("mylogs", "fragment view created ${f.javaClass.simpleName}")
//                                //if fragment is in our list of allowed views, send a serve
//                                if(viewMap.containsKey(f.javaClass.simpleName)){
//                                    getInstance().getPulseInsights().setViewName(viewMap[f.javaClass.simpleName])
//                                    getInstance().getPulseInsights().serve()
//                                }
//                            }
//                        },
//                        true
//                    )
//                }
//            }
//
//            override fun onActivityStarted(activity: Activity) {
//
//
//            }
//
//            override fun onActivityResumed(activity: Activity) {
//                MyApp.getInstance().getPulseInsights().context = activity
//
//            }
//
//            override fun onActivityPaused(activity: Activity) {
//
//            }
//
//            override fun onActivityStopped(activity: Activity) {
//
//            }
//
//            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
//
//            }
//
//            override fun onActivityDestroyed(activity: Activity) {
//
//            }
//        })
//
//    }
//    @Synchronized
//    fun getPulseInsightsDefault(): PulseInsights {
//        if (!::pi.isInitialized) {
//            val piConfig = ExtraConfig()
//            piConfig.automaticStart = false
//            piConfig.customData = HashMap()
//            piConfig.customData["name"] = "tester"
//            piConfig.customData["type"] = "worker"
//            piConfig.customData["age"] = "12"
//            this.pi = PulseInsights(this, "PI-71294199", piConfig)
//        }
//        pi.setDebugMode(true)
//
//        return pi
//    }
//    fun getPulseInsights(): PulseInsights {
//        return getInstance().getPulseInsightsDefault()
//    }


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

