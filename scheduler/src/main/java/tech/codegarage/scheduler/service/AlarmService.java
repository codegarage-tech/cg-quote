package tech.codegarage.scheduler.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import tech.codegarage.scheduler.model.BaseParcelable;
import tech.codegarage.scheduler.model.ScheduleItem;
import tech.codegarage.scheduler.receiver.AlarmReceiver;
import tech.codegarage.scheduler.util.AppUtils;

import static tech.codegarage.scheduler.util.AllConstants.INTENT_ACTION_CANCEL;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_ACTION_CREATE;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_ACTION_DELETE;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_KEY_SCHEDULE_DATA_ALARM_RECEIVER;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_KEY_SCHEDULE_DATA_ALARM_SERVICE;
import static tech.codegarage.scheduler.util.AllConstants.SESSION_KEY_SCHEDULE_DATA;

/**
 * @author Md. Rashadul Alam
 */
public class AlarmService extends IntentService {

    private IntentFilter matcher;
    private ScheduleItem mScheduleItem;
    private String TAG = AlarmService.class.getSimpleName();

    public AlarmService() {
        super(AlarmService.class.getSimpleName());
        matcher = new IntentFilter();
        matcher.addAction(INTENT_ACTION_CREATE);
        matcher.addAction(INTENT_ACTION_DELETE);
        matcher.addAction(INTENT_ACTION_CANCEL);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        mScheduleItem = intent.getParcelableExtra(INTENT_KEY_SCHEDULE_DATA_ALARM_SERVICE);
        if (mScheduleItem != null && matcher.matchAction(action)) {
            executeSchedule(action, mScheduleItem);
        }
    }

    private void executeSchedule(String action, ScheduleItem scheduleItem) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra(INTENT_KEY_SCHEDULE_DATA_ALARM_RECEIVER, BaseParcelable.toByteArray(scheduleItem));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, scheduleItem.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (INTENT_ACTION_CREATE.equals(action)) {

            long timeInMilliseconds = scheduleItem.getTimeInMillis();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pendingIntent);
            }
            AppUtils.setStringSetting(this, SESSION_KEY_SCHEDULE_DATA, ScheduleItem.convertFromObjectToString(scheduleItem));

        } else if (INTENT_ACTION_DELETE.equals(action)) {

            alarmManager.cancel(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(scheduleItem.getId());

        } else if (INTENT_ACTION_CANCEL.equals(action)) {

            alarmManager.cancel(pendingIntent);
        }
    }
}
