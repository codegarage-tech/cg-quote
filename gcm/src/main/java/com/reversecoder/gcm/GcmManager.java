package com.reversecoder.gcm;

import android.content.Context;

/**
 * @author Md. Rashadul Alam
 */
public class GcmManager {

    private static GcmBuilder mGcmBuilder;
    private Context mContext;

    public static GcmManager initGcmManager(Context context, GcmBuilder gcmBuilder) {
        return new GcmManager(context, gcmBuilder);
    }

    private GcmManager(Context context, GcmBuilder gcmBuilder) {
        mContext = context;
        mGcmBuilder = gcmBuilder;
    }

    public static Class<?> getContentClass() {
        return mGcmBuilder.getContentClass();
    }

    public static class GcmBuilder {
        private Class<?> cls;

        public GcmBuilder() {
        }

        public GcmBuilder setContentClass(Class<?> cls) {
            this.cls = cls;
            return this;
        }

        public Class<?> getContentClass() {
            return cls;
        }

        public GcmBuilder buildGcmManager() {
            return this;
        }
    }
}