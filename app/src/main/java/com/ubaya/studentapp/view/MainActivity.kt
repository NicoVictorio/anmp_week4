package com.ubaya.studentapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.resources.Compatibility.Api18Impl.setAutoCancel
import androidx.core.app.NotificationCompat
import com.ubaya.studentapp.R
import com.ubaya.studentapp.databinding.ActivityMainBinding
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.ubaya.studentapp.util.createNotificationChannel

class MainActivity : AppCompatActivity() {
    init{
        instance = this
    }
    companion object{
        private var instance:MainActivity ?= null

        fun showNotification(title:String, content:String, icon:Int){
            val channelId = "${instance?.packageName}-${instance?.getString(R.string.app_name)}"

            val notificationBuilder = NotificationCompat.Builder(instance!!.applicationContext, channelId).apply {
                setSmallIcon(icon)
                setContentTitle(title)
                setContentText(content)
                setStyle(NotificationCompat.BigTextStyle())
                priority = NotificationCompat.PRIORITY_DEFAULT
                setAutoCancel(true)
            }

            val manager = NotificationManagerCompat.from(instance!!.applicationContext)
            //allow deny pop up
            if(ActivityCompat.checkSelfPermission(instance!!.applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(instance!!, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
                return
            }
            manager.notify(1001, notificationBuilder.build())
        }
    }

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode==1){
            if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Log.d("permission", "granted")
                createNotificationChannel(this,
                    NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
                    getString(R.string.app_name), "App notification channel.")
            } else {
                Log.d("permission", "not granted")
            }
        }
    }
}