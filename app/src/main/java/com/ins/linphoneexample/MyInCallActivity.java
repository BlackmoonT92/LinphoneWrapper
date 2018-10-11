package com.ins.linphoneexample;

import android.content.Intent;

import com.ins.linphone.LinphoneWrapper;
import com.ins.linphone.ui.InCallActivity;

public class MyInCallActivity extends InCallActivity {

    @Override
    protected void onCallFinished() {
        finish();
        //LinphoneWrapper.logout();
        //startActivity(new Intent(this, LoginActivity.class));
    }
}
