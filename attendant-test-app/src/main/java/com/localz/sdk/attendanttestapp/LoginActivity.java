package com.localz.sdk.attendanttestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.localz.sdk.attendant.Callback;
import com.localz.sdk.attendant.Error;
import com.localz.sdk.attendant.LocalzAttendantSDK;
import com.localz.sdk.attendant.model.Attendant;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText branchIdEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (LocalzAttendantSDK.getInstance().isLoggedIn(this)) {
            Intent intent = new Intent(LoginActivity.this, ActionsActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        branchIdEditText = findViewById(R.id.branchId);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String branchId = branchIdEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                LocalzAttendantSDK.LoginOptions loginOptions = new LocalzAttendantSDK.LoginOptions();
                loginOptions.setForce(true);

                LocalzAttendantSDK.getInstance().login(LoginActivity.this, username, password, branchId, loginOptions, new Callback<Attendant>() {
                    @Override
                    public void onSuccess(Attendant result) {
                        Log.d(TAG, "login onSuccess");
                        Intent intent = new Intent(LoginActivity.this, ActionsActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(Error error) {
                        Log.d(TAG, "login onError: " + error);
                    }
                });
            }
        });
    }
}
