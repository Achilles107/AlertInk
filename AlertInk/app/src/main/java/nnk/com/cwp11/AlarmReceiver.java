package nnk.com.cwp11;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Achilles on 8/22/2018.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver
{
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        int recId=Integer.parseInt(intent.getStringExtra(SendActivity.EXTRA_REMINDER_ID));

        //Get notification title
        MyDatabaseClass myDatabaseClass=new MyDatabaseClass(context);
        Reminder reminder=myDatabaseClass.getReminder(recId);
        String title="Happy Birthday "+reminder.getRem_name();

        //Create intent to open on notification click
        Intent intent1=new Intent(context,SendActivity.class);
        intent1.putExtra("name",reminder.getRem_name());
        intent1.putExtra("phone",reminder.getRem_phone());
        intent1.putExtra("message",reminder.getRem_message());
        intent1.putExtra(SendActivity.EXTRA_REMINDER_ID,Integer.toString(recId));
        PendingIntent click=PendingIntent.getActivity(context,recId,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        //Create notification
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_action_user_add)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setTicker(title)
                .setContentText(title)
                .setContentIntent(click)
                .setAutoCancel(true);
        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(recId,builder.build());


    }

    public void setAlarm(Context context,Calendar calendar,int id)
    {
        alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //Put id in intent Extra
        Intent intent=new Intent(context,AlarmReceiver.class);
        intent.putExtra(SendActivity.EXTRA_REMINDER_ID,Integer.toString(id));
        pendingIntent=PendingIntent.getBroadcast(context,id,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        //Calculate notification time
        Calendar calendar1=Calendar.getInstance();
        long currTime=calendar1.getTimeInMillis();
        long diffTime=calendar.getTimeInMillis()-currTime;

        //Start alarm using notification time
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + diffTime, pendingIntent);
        /*SystemClock.elapsedRealtime()+diffTime*/
    }

    public void cancelAlarm(Context context,int ID)
    {
        alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //Cancel Alarm
        pendingIntent=PendingIntent.getBroadcast(context,ID,new Intent(context,AlarmReceiver.class),0);
        alarmManager.cancel(pendingIntent);

        //Disable alarm
        //ComponentName componentName=new ComponentName(context,)
    }
}
