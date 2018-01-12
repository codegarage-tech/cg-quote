package tech.codegarage.scheduler.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.Calendar;

import tech.codegarage.scheduler.R;
import tech.codegarage.scheduler.Scheduler;
import tech.codegarage.scheduler.enumeration.REPEAT_TYPE;
import tech.codegarage.scheduler.model.BaseParcelable;
import tech.codegarage.scheduler.model.ScheduleItem;
import tech.codegarage.scheduler.service.AlarmService;

import static tech.codegarage.scheduler.util.AllConstants.DATE_FORMAT;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_ACTION_CREATE;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_KEY_SCHEDULE_DATA_ALARM_RECEIVER;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_KEY_SCHEDULE_DATA_ALARM_SERVICE;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_KEY_SCHEDULE_DATA_CONTENT_INTENT;

/**
 * @author Md. Rashadul Alam
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

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

        REPEAT_TYPE frequency = scheduleItem.getFrequency();
        if (frequency != REPEAT_TYPE.NONE) {

            Calendar repeatTime = Calendar.getInstance();
            repeatTime.setTimeInMillis(scheduleItem.getTimeInMillis());

            switch (frequency) {
                case MINUTELY:
                    repeatTime.add(Calendar.MINUTE, 1);
                    break;
                case HOURLY:
                    repeatTime.add(Calendar.HOUR, 1);
                    break;
                case DAILY:
                    repeatTime.add(Calendar.DATE, 1);
                    break;
                case WEEKLY:
                    repeatTime.add(Calendar.DATE, 7);
                    break;
                case MONTHLY:
                    repeatTime.add(Calendar.MONTH, 1);
                    break;
                case YEARLY:
                    repeatTime.add(Calendar.YEAR, 1);
                    break;
            }

            //Set updated schedule
            String mDate = DATE_FORMAT.format(repeatTime.getTime());
            ScheduleItem updatedSchedule = new ScheduleItem(scheduleItem.getId(), scheduleItem.getTitle(), scheduleItem.getContent(), mDate, repeatTime.getTimeInMillis(), scheduleItem.getFrequency());

            //Set alarm again for repeating
            Intent setAlarm = new Intent(context, AlarmService.class);
            setAlarm.putExtra(INTENT_KEY_SCHEDULE_DATA_ALARM_SERVICE, updatedSchedule);
            setAlarm.setAction(INTENT_ACTION_CREATE);
            context.startService(setAlarm);
        }

        sendNotification(context, scheduleItem);
    }

    private void sendNotification(Context context, ScheduleItem scheduleItem) {
        Intent intentQuoteOfTheDay = new Intent(context, Scheduler.getContentClass());
        intentQuoteOfTheDay.putExtra(INTENT_KEY_SCHEDULE_DATA_CONTENT_INTENT, scheduleItem);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(Scheduler.getContentClass());
//        stackBuilder.addNextIntent(result);
//        PendingIntent clicked = stackBuilder.getPendingIntent(scheduleItem.getId(), PendingIntent.FLAG_UPDATE_CURRENT);
        intentQuoteOfTheDay.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntentQuoteOfTheDay = PendingIntent.getActivity(context, scheduleItem.getId(), intentQuoteOfTheDay, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.setBigContentTitle(scheduleItem.getTitle() + "(" + scheduleItem.getAlarmTime() + ")");
        bigStyle.bigText(scheduleItem.getContent());
        Notification n = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notification_quote)
                .setContentTitle(scheduleItem.getTitle() + "(" + scheduleItem.getAlarmTime() + ")")
                .setContentText(scheduleItem.getContent())
                .setPriority(Notification.PRIORITY_MAX)
                .setWhen(0)
                .setStyle(bigStyle)
                .setContentIntent(pendingIntentQuoteOfTheDay)
                .setAutoCancel(true)
                .build();

        n.defaults |= Notification.DEFAULT_VIBRATE;
        n.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        n.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(scheduleItem.getId(), n);
    }
}