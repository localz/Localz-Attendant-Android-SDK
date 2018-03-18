package com.localz.sdk.attendanttestapp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.localz.sdk.attendant.model.Order;
import com.localz.sdk.attendant.receiver.OrderStatusChangeReceiver;

public class OrderStatusChangeReceiverImpl extends OrderStatusChangeReceiver {

    private static final String TAG = "OrderStatusChange";

    @Override
    protected void onOrderStatusUpdated(@NonNull Order order) {
        Log.d(TAG, "onOrderStatusUpdated " + order);
    }
}
