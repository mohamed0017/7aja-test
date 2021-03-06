package com.haja.haja

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.util.Log
import com.haja.haja.View.ui.ProductDetails.ProductDetailsActivity
import com.haja.haja.View.ui.SplashScreen.SplashActivity
import com.onesignal.OneSignal

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // OneSignal Initialization
        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .setNotificationReceivedHandler {
                val data = it.payload.additionalData
                Log.i("notification data ", data.toString())
            }
            .setNotificationOpenedHandler {
                val data = it.notification.payload.additionalData
                if (data.getInt("type") == 1) {
                    val productId = data.getString("id")
                    val intent = Intent(applicationContext, SplashActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("productId", productId.toInt())
                    intent.putExtra("fromNotification", true)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, SplashActivity::class.java)
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            .init()
    }
}