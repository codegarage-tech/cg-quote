package tech.codegarage.scheduler.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import tech.codegarage.scheduler.model.ScheduleItem;
import tech.codegarage.scheduler.service.AlarmService;
import tech.codegarage.scheduler.util.AppUtils;

import static tech.codegarage.scheduler.util.AllConstants.INTENT_ACTION_CREATE;
import static tech.codegarage.scheduler.util.AllConstants.INTENT_KEY_SCHEDULE_DATA_ALARM_SERVICE;
import static tech.codegarage.scheduler.util.AllConstants.SESSION_DEFAULT_VALUE_STRING;
import static tech.codegarage.scheduler.util.AllConstants.SESSION_KEY_SCHEDULE_DATA;

/**
 * @author Md. Rashadul Alam
 */
public class AlarmSetter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!AppUtils.isNullOrEmpty(AppUtils.getStringSetting(context, SESSION_KEY_SCHEDULE_DATA, SESSION_DEFAULT_VALUE_STRING))) {
            ScheduleItem scheduleItem = ScheduleItem.convertFromStringToObject(AppUtils.getStringSetting(context, SESSION_KEY_SCHEDULE_DATA, SESSION_DEFAULT_VALUE_STRING), ScheduleItem.class);

            if ((scheduleItem != null) && (scheduleItem.getTimeInMillis() > Calendar.getInstance().getTimeInMillis())) {
                Intent service = new Intent(context, AlarmService.class);
                service.putExtra(INTENT_KEY_SCHEDULE_DATA_ALARM_SERVICE, scheduleItem);
                service.setAction(INTENT_ACTION_CREATE);
                context.startService(service);
            }
        }
    }
}