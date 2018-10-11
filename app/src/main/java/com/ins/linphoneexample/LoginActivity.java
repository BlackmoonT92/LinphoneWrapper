package com.ins.linphoneexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.ins.linphone.LinphoneWrapper;
import com.ins.linphone.callback.RegistrationCallback;
import com.ins.linphone.service.LinphoneService;
import com.ins.linphone.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.sip_account) EditText mAccount;
    @BindView(R.id.sip_password) EditText mPassword;
    @BindView(R.id.sip_server) EditText mServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if(!LinphoneService.isReady()){
            LinphoneWrapper.startLinphoneService(this);
        }else if(LinphoneService.isloggedin()){
            goToMainActivity();
        }
        LinphoneWrapper.addCallback(new RegistrationCallback() {
            @Override
            public void OnRegistrationOk() {
                super.OnRegistrationOk();
                LogUtil.d( "registrationOk: ");
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                goToMainActivity();
            }

            @Override
            public void OnRegistrationFailed() {
                super.OnRegistrationFailed();
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();

            }
        }, null);
    }

    @OnClick(R.id.press_login)
    public void login() {
        String account = mAccount.getText().toString();
        String password = mPassword.getText().toString();
        String serverIP = mServer.getText().toString();
        LinphoneWrapper.login(account, password, serverIP);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, DialerActivity.class);
        intent.putExtra("EXTRA_REGION_CODE", "AU");
        startActivity(intent); 
    }
}
