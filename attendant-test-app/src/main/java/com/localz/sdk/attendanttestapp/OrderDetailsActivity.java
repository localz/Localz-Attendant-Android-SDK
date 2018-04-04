package com.localz.sdk.attendanttestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.localz.sdk.attendant.Callback;
import com.localz.sdk.attendant.Error;
import com.localz.sdk.attendant.LocalzAttendantSDK;
import com.localz.sdk.attendant.model.Order;
import com.localz.sdk.attendant.model.OrderId;

public class OrderDetailsActivity extends AppCompatActivity {

    public static final String TAG = "OrderDetailsActivity";

    public static Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        if (order == null) {
            return;
        }
        updateView(order);

        ((Button) findViewById(R.id.complete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalzAttendantSDK.getInstance().completeOrder(OrderDetailsActivity.this, OrderId.forOrder(order), "signature", "notes", null, new Callback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        Log.d(TAG, "completeOrder onSuccess");
                        refreshOrderDetails();
                    }

                    @Override
                    public void onError(Error error) {
                        Log.d(TAG, "completeOrder onError: " + error);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.acknowledge)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalzAttendantSDK.getInstance().acknowledgeOrder(OrderDetailsActivity.this, OrderId.forOrder(order), new Callback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        Log.d(TAG, "acknowledgeOrder onSuccess");
                        refreshOrderDetails();
                    }

                    @Override
                    public void onError(Error error) {
                        Log.d(TAG, "acknowledgeOrder onError: " + error);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.reset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalzAttendantSDK.getInstance().resetOrder(OrderDetailsActivity.this, OrderId.forOrder(order), new Callback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        Log.d(TAG, "resetOrder onSuccess");
                        refreshOrderDetails();
                    }

                    @Override
                    public void onError(Error error) {
                        Log.d(TAG, "resetOrder onError: " + error);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.checkin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalzAttendantSDK.getInstance().checkinOrder(OrderDetailsActivity.this, OrderId.forOrder(order), "CC", new Callback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        Log.d(TAG, "checkinOrder onSuccess");
                        refreshOrderDetails();
                    }

                    @Override
                    public void onError(Error error) {
                        Log.d(TAG, "checkinOrder onError: " + error);
                    }
                });
            }
        });
    }

    private void refreshOrderDetails() {
        LocalzAttendantSDK.getInstance().getOrderDetails(OrderDetailsActivity.this, OrderId.forOrder(order), new Callback<Order>() {
            @Override
            public void onSuccess(Order result) {
                Log.d(TAG, "getOrderDetails onSuccess");
                OrderDetailsActivity.order = result;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateView(OrderDetailsActivity.order);
                    }
                });
            }

            @Override
            public void onError(Error error) {
                Log.d(TAG, "getOrderDetails onError: " + error);
            }
        });
    }

    private void updateView(Order order) {
        ((TextView) findViewById(R.id.orderNumber)).setText(order.orderNumber);
        ((TextView) findViewById(R.id.orderStatus)).setText("" + order.orderStatus);
    }
}
