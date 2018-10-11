package com.ins.linphoneexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ins.linphone.LinphoneWrapper;
import com.ins.linphone.utils.LinphoneConstants;
import com.ins.linphone.utils.LogUtil;
import com.ins.linphone.ui.IncomingCallActivity;
import com.ins.linphone.utils.Utils;

public class MyIncomingCallActivity extends IncomingCallActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onAnswerCallClicked() {
        try{
            LinphoneWrapper.acceptCall();
            if (LinphoneWrapper.getVideoEnabled()) {
                Intent intent = new Intent(MyIncomingCallActivity.this, MyInCallActivity.class);
                intent.putExtra(LinphoneConstants.IntentKeys.CALLER_NUMBER, tvPhoneNumber.getText().toString());
                startActivity(intent);
            }else{
                //start audio screen
                Intent intent = new Intent(MyIncomingCallActivity.this, MyInCallActivity.class);
                intent.putExtra(LinphoneConstants.IntentKeys.CALLER_NUMBER, tvPhoneNumber.getText().toString());
                startActivity(intent);
            }
            finish();
        }catch (Exception e){
            LogUtil.e(e.getMessage());
        }
    }

    @Override
    protected void onHangUpClicked() {
        LinphoneWrapper.hangUp();
        Utils.startActivityOnMainThread(this, new Intent(this, MainActivity.class));
        finish();
    }

}
