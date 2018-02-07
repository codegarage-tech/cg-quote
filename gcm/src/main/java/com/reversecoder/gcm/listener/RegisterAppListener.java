package com.reversecoder.gcm.listener;

import com.reversecoder.gcm.util.HttpRequestManager;

/**
 * @author Md. Rashadul Alam
 *         Email: rashed.droid@gmail.com
 */
public interface RegisterAppListener {
    public void registerApp(HttpRequestManager.HttpResponse result);
}