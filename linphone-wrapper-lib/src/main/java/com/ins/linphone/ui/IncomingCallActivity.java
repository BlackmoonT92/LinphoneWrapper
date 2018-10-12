package com.ins.linphone.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.linphone.LinphoneWrapper;
import com.ins.linphone.callback.PhoneCallback;
import com.ins.linphone.utils.LinphoneConstants;
import com.ins.linphone.utils.Utils;
import com.ins.linphone.R;

public abstract class IncomingCallActivity extends BaseActivity {

    protected ImageView ivAnswerCall;
    protected ImageView ivHangup;
    protected TextView tvPhoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Since this is a dialog we need to have FLAG_TURN_SCREEN_ON and FLAG_SHOW_WHEN_LOCKED in the other activities
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incommingcall);

        ivAnswerCall = (ImageView) findViewById(R.id.ivAnswer);
        ivHangup = (ImageView) findViewById(R.id.ivHangUp);
        tvPhoneNumber = (TextView) findViewById(R.id.tvCallerNumber);

        ivAnswerCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerCallClicked();
            }
        });

        ivHangup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHangUpClicked();
            }
        });

        String callerNumber = getIntent().getStringExtra(LinphoneConstants.IntentKeys.CALLER_NUMBER);
        if(callerNumber != null){
            setCallerNumber(Utils.getUsernameFromSipAddress(callerNumber));
        }
    }


    protected abstract void onHangUpClicked();

    protected abstract void onAnswerCallClicked();

    public void setCallerNumber(@NonNull String phoneNumber) {
        tvPhoneNumber.setText(phoneNumber);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinphoneWrapper.addCallback(null, new PhoneCallback() {
            @Override
            public void OnCallReleased() {
                super.OnCallReleased();
                finish();
            }
        });
    }
}
