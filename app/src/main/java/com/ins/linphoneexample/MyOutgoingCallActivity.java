package com.ins.linphoneexample;

import android.content.Intent;

import com.ins.linphone.LinphoneWrapper;
import com.ins.linphone.ui.OutgoingCallActivity;
import com.ins.linphone.utils.LinphoneConstants;

public class MyOutgoingCallActivity extends OutgoingCallActivity {


    @Override
    protected void onHangUpClicked() {
        LinphoneWrapper.hangUp();
        finish();
    }

    @Override
    protected void onOutgoingCallConnected() {
        Intent intent = new Intent(MyOutgoingCallActivity.this, MyInCallActivity.class);
        intent.putExtra(LinphoneConstants.IntentKeys.CALLER_NUMBER, tvDialingNumber.getText().toString());
        startActivity(intent);
        finish();
    }
}
