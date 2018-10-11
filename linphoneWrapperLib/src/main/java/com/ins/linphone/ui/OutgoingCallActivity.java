package com.ins.linphone.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ins.linphone.LinphoneWrapper;
import com.ins.linphone.R;
import com.ins.linphone.callback.PhoneCallback;
import com.ins.linphone.utils.LinphoneConstants;
import com.ins.linphone.utils.Utils;

public abstract class OutgoingCallActivity extends BaseActivity {
    protected TextView tvDialingNumber;
    protected ImageView ivHangUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_outgoingcall);
        tvDialingNumber = (TextView) findViewById(R.id.tvDialingNumber);
        ivHangUp = (ImageView) findViewById(R.id.ivHangUp);

        ivHangUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHangUpClicked();
            }
        });

        String dialingNumber = getIntent().getStringExtra(LinphoneConstants.IntentKeys.CALLER_NUMBER);
        if(dialingNumber != null){
            setDialingNumber(Utils.getUsernameFromSipAddress(dialingNumber));
        }

        LinphoneWrapper.callTo(dialingNumber, false);
    }

    private void setDialingNumber(String number) {
        tvDialingNumber.setText(number);
    }

    protected abstract void onHangUpClicked();

    @Override
    protected void onResume() {
        super.onResume();
        LinphoneWrapper.addCallback(null, new PhoneCallback() {
            @Override
            public void onCallConnected() {
                super.onCallConnected();
                onOutgoingCallConnected();
            }

            @Override
            public void OnCallReleased() {
                super.OnCallReleased();
                finish();
            }

            @Override
            public void onCallError() {
                super.onCallError();
                Utils.showToast(OutgoingCallActivity.this, "Something when wrong", Toast.LENGTH_LONG);
                finish();
            }
        });
    }

    protected abstract void onOutgoingCallConnected();
}
