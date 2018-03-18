package com.localz.sdk.attendanttestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.localz.sdk.attendant.Callback;
import com.localz.sdk.attendant.Error;
import com.localz.sdk.attendant.LocalzAttendantSDK;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    public static final String PROJECT_ID = "...";
    public static final String SPOTZ_PROJECT_KEY = "...";
    public static final String ATTENDANT_KEY = "...";
    public static final String ENVIRONMENT = "development"; // or "production"

    public static final String GCM_PROJECT_ID = "...";

    public static final String PUSHER_KEY = "...";
    public static final String PUSHER_CLUSTER = "...";

    Button initializeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButton = findViewById(R.id.btn_do_it);
        initializeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSdk();
            }
        });
    }

    private void initSdk() {
        LocalzAttendantSDK.Configuration configuration = new LocalzAttendantSDK.Configuration()
                .setProjectId(PROJECT_ID)
                .setSpotzProjectKey(SPOTZ_PROJECT_KEY)
                .setCncProjectKey(ATTENDANT_KEY)
                .setEnvironment(ENVIRONMENT)
                .setPushServiceConfiguration(PushServiceConfigurationProvider.get());
        LocalzAttendantSDK.getInstance().init(this, configuration, new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Log.d(TAG, "init onSuccess");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Error error) {
                Log.d(TAG, "init onError: " + error);
            }
        });
    }

}
