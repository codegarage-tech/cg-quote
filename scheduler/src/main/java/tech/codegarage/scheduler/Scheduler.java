package tech.codegarage.scheduler;

import android.content.Context;

/**
 * @author Md. Rashadul Alam
 */
public class Scheduler {

    private static ScheduleBuilder mScheduleBuilder;
    private Context mContext;

    public static Scheduler initSchedule(Context context, ScheduleBuilder scheduleBuilder) {
        return new Scheduler(context, scheduleBuilder);
    }

    private Scheduler(Context context, ScheduleBuilder scheduleBuilder) {
        mContext = context;
        mScheduleBuilder = scheduleBuilder;
    }

    public static Class<?> getContentClass() {
        return mScheduleBuilder.getContentClass();
    }

    public static class ScheduleBuilder {
        private Class<?> cls;

        public ScheduleBuilder() {
        }

        public ScheduleBuilder setContentClass(Class<?> cls) {
            this.cls = cls;
            return this;
        }

        public Class<?> getContentClass() {
            return cls;
        }

        public ScheduleBuilder buildSchedule() {
            return this;
        }
    }
}