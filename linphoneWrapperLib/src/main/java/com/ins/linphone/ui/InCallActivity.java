package com.ins.linphone.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.linphone.LinphoneWrapper;
import com.ins.linphone.callback.PhoneCallback;
import com.ins.linphone.utils.CountUpTimer;
import com.ins.linphone.R;
import com.ins.linphone.utils.LinphoneConstants;
import com.ins.linphone.utils.Utils;

public abstract class InCallActivity extends BaseActivity {

    protected TextView tvTimer;
    protected TextView tvCallerNumber;
    protected ImageView ivHangUp;


    CountUpTimer timer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_incall);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvCallerNumber = (TextView) findViewById(R.id.tvCallerNumber);
        ivHangUp = (ImageView) findViewById(R.id.ivHangUp);

        ivHangUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinphoneWrapper.hangUp();
            }
        });

        String callerNumber = getIntent().getStringExtra(LinphoneConstants.IntentKeys.CALLER_NUMBER);
        if(callerNumber != null){
            setCallerNumber(Utils.getUsernameFromSipAddress(callerNumber));
        }
        startTimer();
    }

    private void setCallerNumber(String usernameFromSipAddress) {
        tvCallerNumber.setText(usernameFromSipAddress);
    }

    private void startTimer() {
        timer = new CountUpTimer(1000) {
            @Override
            public void onTick(int second) {
                tvTimer.setText(Utils.secondsToString(second));
            }
        };
        timer.start();
    }

    protected abstract void onCallFinished();

    @Override
    protected void onResume() {
        super.onResume();
        LinphoneWrapper.addCallback(null, new PhoneCallback() {
            @Override
            public void OnCallReleased() {
                super.OnCallReleased();
                onCallFinished();
            }
        });
    }


}
