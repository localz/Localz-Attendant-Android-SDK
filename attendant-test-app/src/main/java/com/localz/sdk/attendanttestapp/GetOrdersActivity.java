package com.localz.sdk.attendanttestapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.localz.sdk.attendant.Callback;
import com.localz.sdk.attendant.Error;
import com.localz.sdk.attendant.LocalzAttendantSDK;
import com.localz.sdk.attendant.model.Order;
import com.localz.sdk.attendant.model.OrderId;
import com.localz.sdk.attendant.model.OrderList;

public class GetOrdersActivity extends AppCompatActivity {

    public static final String TAG = "GetOrdersActivity";

    private LinearLayout orderList;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_orders);

        orderList = findViewById(R.id.order_list);

        handler.post(new Runnable() {
            @Override
            public void run() {
                LocalzAttendantSDK.getInstance().retrieveOrders(GetOrdersActivity.this, new Callback<OrderList>() {
                    @Override
                    public void onSuccess(final OrderList result) {
                        Log.d(TAG, "retrieveOrders onSuccess");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateOrders(result);
                            }
                        });
                    }

                    @Override
                    public void onError(Error error) {
                        Log.d(TAG, "retrieveOrders onError " + error);
                    }
                });
            }
        });
    }

    private void populateOrders(OrderList result) {
        orderList.removeAllViews();
        if (result.orders != null) {
            for (Order order : result.orders) {
                View view = getLayoutInflater().inflate(R.layout.view_order_list_item, orderList, false);

                TextView orderNumberTextView = view.findViewById(R.id.orderNumber);
                orderNumberTextView.setText(order.orderNumber);

                TextView orderStatusTextView = view.findViewById(R.id.orderStatus);
                orderStatusTextView.setText("" + order.orderStatus);

                view.setTag(order);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getOrderDetails((Order) v.getTag());
                    }
                });

                orderList.addView(view);
            }
        }
    }

    private void getOrderDetails(Order order) {
        LocalzAttendantSDK.getInstance().getOrderDetails(GetOrdersActivity.this, OrderId.forOrder(order), new Callback<Order>() {
            @Override
            public void onSuccess(Order result) {
                Log.d(TAG, "getOrderDetails onSuccess");
                OrderDetailsActivity.order = result;

                Intent intent = new Intent(GetOrdersActivity.this, OrderDetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(Error error) {
                Log.d(TAG, "getOrderDetails onError: " + error);
            }
        });
    }
}
