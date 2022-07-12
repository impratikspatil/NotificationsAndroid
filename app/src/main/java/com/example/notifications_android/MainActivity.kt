package com.example.notifications_android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import com.example.notifications_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //every channel should have different id and name therefore I hace created these

    val CHANNEL_ID="ChannelID"
    val CHANNEL_NAME="ChannelNAME"
    val NOTIFICATION_id=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        createNotificationChannel() //calling function which create notification channel

        //here we have created pending intents which is used by other apps to run this code
        //so basically notifications are run by notification manager app(which is different one app)
        // so when notification will arise and we click on that notification then it should redirect to the app, to do so
        // we have used pending intents which due to this notification manager will use that pending intent and run this small code to redirect user to the app from which notification has sent
        val intent= Intent(this,MainActivity::class.java)
        val pendingIntent=TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT)
        }






        // the below  notificatio varibale contain actual notification(with title and content and icon)
        // that we want to show on screen
        val notification=NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("IMP notification")
                .setContentText("this is imp notification")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()
        //this build() function return this notificaiton and stored it into notification variable





        //it is mandatory to show notification
        val notificationManager=NotificationManagerCompat.from(this)






        //when we click on btn shownotification then below line will get executed which
        //take notificaiton id and notification itselt and by using notificaitonManager
        // it will show notificationj
        binding.showNotification.setOnClickListener {
            notificationManager.notify(NOTIFICATION_id,notification)
        }



    }

    //this function is used for creating notification channel
    fun createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) //To check whether app is running or OREO or later versions
        {
            //we have to create channel
            val channel=NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH).apply {
                    lightColor=Color.RED
                   enableLights(true)

            }

            //so below two lines of code will actually create notification channel
            val manager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)



        }

    }

}
// those devices which runs on higher version than android oreo needs notification channel to show notification
// before this version showing notifications was very easy


//steps:-
//1. Create notification channel first
//2.create or make notification that we want to show on screen (with all config like title, context, icon, priority,id etc)
//3.create notification Manager to show notificaiton
//4.show notification on clicking button
//5.also pending intents are created to redirect user to app screen when he/she click on that notification