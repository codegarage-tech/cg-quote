package tech.codegarage.scheduler.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.Calendar;

import tech.codegarage.scheduler.R;
import tech.codegarage.scheduler.model.BaseParcelable;
import tech.codegarage.scheduler.model.ScheduleItem;
import tech.codegarage.scheduler.service.AlarmService;

import static tech.codegarage.scheduler.util.AllConstants.INTENT_ACTION_CREATE;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_KEY_SCHEDULE_DATA_ALARM_RECEIVER;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_KEY_SCHEDULE_DATA_ALARM_SERVICE;

/**
 * @author Md. Rashadul Alam
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static final int HOURLY = 1, DAILY = 2, WEEKLY = 3, MONTHLY = 4, YEARLY = 5;
    private ScheduleItem scheduleItem;
    private String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        scheduleItem = (ScheduleItem) BaseParcelable.toParcelable(intent.getByteArrayExtra(INTENT_KEY_SCHEDULE_DATA_ALARM_RECEIVER), ScheduleItem.CREATOR);

        if (context == null) {
            return;
        }

        if (scheduleItem == null) {
            return;
        }

        int frequency = scheduleItem.getFrequency();
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(scheduleItem.getTimeInMillis());

        if (frequency > 0) {
            if (frequency == HOURLY) {
                time.add(Calendar.HOUR, 1);

            } else if (frequency == DAILY) {
                time.add(Calendar.DATE, 1);

            } else if (frequency == WEEKLY) {
                time.add(Calendar.DATE, 7);
            } else if (frequency == MONTHLY) {
                time.add(Calendar.MONTH, 1);

            } else if (frequency == YEARLY) {
                time.add(Calendar.YEAR, 1);

            }

            //Set alarm again for repeating
            Intent setAlarm = new Intent(context, AlarmService.class);
            setAlarm.putExtra(INTENT_KEY_SCHEDULE_DATA_ALARM_SERVICE, scheduleItem);
            setAlarm.setAction(INTENT_ACTION_CREATE);
            context.startService(setAlarm);
        }

        sendNotification(context, scheduleItem);
    }

    private void sendNotification(Context context, ScheduleItem scheduleItem) {
//        Intent result = new Intent(context, CreateOrEditAlert.class);
//        result.putExtra(ReminderParams.ID, id);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(CreateOrEditAlert.class);
//        stackBuilder.addNextIntent(result);
//        PendingIntent clicked = stackBuilder.getPendingIntent(scheduleItem.getId(), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.setBigContentTitle(scheduleItem.getTitle());
        bigStyle.bigText(scheduleItem.getContent());
        Notification n = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_schedule_48)
                .setContentTitle(scheduleItem.getTitle())
                .setContentText(scheduleItem.getContent())
                .setPriority(Notification.PRIORITY_MAX)
                .setWhen(0)
                .setStyle(bigStyle)
//                .setContentIntent(clicked)
                .setAutoCancel(true)
                .build();


        n.defaults |= Notification.DEFAULT_VIBRATE;
        n.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        n.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(scheduleItem.getId(), n);
    }
}
