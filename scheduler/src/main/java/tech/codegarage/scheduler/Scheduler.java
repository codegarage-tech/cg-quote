package tech.codegarage.scheduler;

/**
 * @author Md. Rashadul Alam
 */
public class Scheduler {

    public static Class<?> clazz;

    private Scheduler(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getContentClass() {
        return clazz;
    }

    public static class ScheduleBuilder {
        private Class<?> cls;

        public ScheduleBuilder setContentClass(Class<?> cls) {
            this.cls = cls;
            return this;
        }

        public Scheduler buildSchedule() {
            return new Scheduler(cls);
        }
    }
}