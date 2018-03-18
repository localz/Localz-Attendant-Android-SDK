package com.localz.sdk.attendanttestapp;

import com.localz.spotzpush.sdk.service.PushServiceConfiguration;

import static com.localz.sdk.attendanttestapp.MainActivity.ENVIRONMENT;
import static com.localz.sdk.attendanttestapp.MainActivity.GCM_PROJECT_ID;
import static com.localz.sdk.attendanttestapp.MainActivity.PROJECT_ID;
import static com.localz.sdk.attendanttestapp.MainActivity.SPOTZ_PROJECT_KEY;

public class PushServiceConfigurationProvider {

    static PushServiceConfiguration get() {
        PushServiceConfiguration pushServiceConfiguration = new PushServiceConfiguration(PROJECT_ID, SPOTZ_PROJECT_KEY, ENVIRONMENT);
        pushServiceConfiguration.setGcmConfiguration(GCM_PROJECT_ID);
        return pushServiceConfiguration;
    }
}
