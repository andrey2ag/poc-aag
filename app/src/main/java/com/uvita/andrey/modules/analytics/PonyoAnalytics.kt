package com.uvita.andrey.modules.analytics

//import com.google.firebase.analytics.FirebaseAnalytics
import android.os.Bundle
import com.uvita.andrey.BuildConfig
import com.uvita.andrey.general.LogUtil
import java.util.*

const val TAG = "PonyoAnalytics"

object PonyoAnalytics {
    init {
        setupDefaultParameters()
    }

    private fun logEvent(eventName: String, params: Bundle?) {
        LogUtil.LOGV(TAG, "eventName: $eventName, params: $params")
//        firebaseAnalytics.logEvent(eventName, params)
    }

    private fun setupDefaultParameters() {
        val parameters = Bundle().apply {
            this.putString("device_id", getDeviceId())
        }
        LogUtil.LOGV(TAG, "defaultParameters: $parameters")
//        firebaseAnalytics.setDefaultEventParameters(parameters)
    }

    private fun getDeviceId(): String {
        return UUID.randomUUID().toString()
    }

    fun trackAppLaunch() {
        val params = Bundle().apply {
            putString("version", BuildConfig.VERSION_NAME)
        }
        logEvent("app_started", params)
    }

}