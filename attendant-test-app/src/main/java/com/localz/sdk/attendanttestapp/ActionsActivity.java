package com.localz.sdk.attendanttestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.localz.sdk.attendant.Callback;
import com.localz.sdk.attendant.Error;
import com.localz.sdk.attendant.LocalzAttendantSDK;

public class ActionsActivity extends AppCompatActivity {

    private static final String TAG = "ActionsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);

        findViewById(R.id.createOrder).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActionsActivity.this, CreateOrderActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.listOrders).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActionsActivity.this, GetOrdersActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalzAttendantSDK.getInstance().logout(ActionsActivity.this, true, new Callback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        Log.d(TAG, "logout onSuccess");
                        Intent intent = new Intent(ActionsActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(Error error) {
                        Log.d(TAG, "logout onError: " + error);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final LocalzAttendantSDK localzAttendantSDK = LocalzAttendantSDK.getInstance();

                ((TextView) findViewById(R.id.is_initialised)).setText(
                        "Initialised: " + localzAttendantSDK.isInitialised(ActionsActivity.this)
                );

                ((TextView) findViewById(R.id.is_logged_in)).setText(
                        "Logged in: " + localzAttendantSDK.isLoggedIn(ActionsActivity.this)
                );

                ((TextView) findViewById(R.id.has_local_changes)).setText(
                        "Has local changes: " + localzAttendantSDK.isCachedData(ActionsActivity.this)
                );
            }
        });
    }
}
