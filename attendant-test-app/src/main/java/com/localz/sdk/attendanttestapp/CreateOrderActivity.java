package com.localz.sdk.attendanttestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.localz.sdk.attendant.Callback;
import com.localz.sdk.attendant.Error;
import com.localz.sdk.attendant.LocalzAttendantSDK;
import com.localz.sdk.attendant.model.Order;
import com.localz.sdk.attendant.model.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

public class CreateOrderActivity extends AppCompatActivity {

    private static final String TAG = "CreateOrderActivity";

    private EditText orderNumber;
    private EditText subProjectId;
    private EditText orderAmount;
    private EditText deliveryName;
    private EditText deliveryEmail;
    private EditText deliveryPhone;
    private EditText shopperId;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        orderNumber = findViewById(R.id.orderNumber);
        subProjectId = findViewById(R.id.subProjectId);
        orderAmount = findViewById(R.id.orderAmount);
        deliveryName = findViewById(R.id.deliveryName);
        deliveryEmail = findViewById(R.id.deliveryEmail);
        deliveryPhone = findViewById(R.id.deliveryPhone);
        shopperId = findViewById(R.id.shopperId);
        address = findViewById(R.id.address);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order();
                order.orderStatus = OrderStatus.PENDING;
                order.orderDate = new Date();
                order.pickupStart = new Date();
                order.pickupEnd = new Date(System.currentTimeMillis() + 1000000);
                order.totalItems = 0;
                order.pickupLocation = "Entrance";
                order.currency = "AUD";
                order.orderNumber = orderNumber.getText().toString();
                order.subProjectId = subProjectId.getText().toString();
                order.orderAmount = new BigDecimal(orderAmount.getText().toString());
                order.deliveryName = deliveryName.getText().toString();
                order.deliveryEmail = deliveryEmail.getText().toString();
                order.deliveryPhone = deliveryPhone.getText().toString();
                order.shopperId = shopperId.getText().toString();
                order.specific = new HashMap<>();
                order.specific.put("address", address.getText().toString());

                LocalzAttendantSDK.getInstance().createOrder(CreateOrderActivity.this, order, new Callback<Order>() {

                    @Override
                    public void onSuccess(Order result) {
                        Log.d(TAG, "createOrder onSuccess");
                        CreateOrderActivity.this.finish();
                    }

                    @Override
                    public void onError(Error error) {
                        Log.d(TAG, "createOrder onError: " + error);
                    }
                });
            }
        });
    }
}
