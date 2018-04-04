package com.localz.sdk.attendanttestapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.localz.sdk.attendant.model.Order;
import com.localz.sdk.attendant.receiver.StateChangeReceiver;

public class StateChangeReceiverImpl extends StateChangeReceiver {

    private static final String TAG = "StateChangeReceiver";

    @Override
    protected void onStateChange(@NonNull State state, @Nullable Order order) {
        Log.d(TAG, "onStateChange state: " + state + ", order: " + order);
    }
}
