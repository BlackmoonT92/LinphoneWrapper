package com.ins.linphoneexample;

import android.content.Intent;

import com.ins.linphone.LinphoneWrapper;
import com.ins.linphone.callback.PhoneCallback;
import com.ins.linphone.utils.LinphoneConstants;
import com.ins.linphone.utils.Utils;

import org.linphone.core.LinphoneCall;

import im.dlg.dialer.DialpadActivity;

public class DialerActivity extends DialpadActivity {

    @Override
    public void ok(String formatted, String raw) {
        super.ok(formatted, raw);
        Intent intentCall = new Intent(this, MyOutgoingCallActivity.class);
        intentCall.putExtra(LinphoneConstants.IntentKeys.CALLER_NUMBER, raw);
        startActivity(intentCall);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinphoneWrapper.addCallback(null, new PhoneCallback() {
            @Override
            public void onIncomingCall(LinphoneCall linphoneCall) {
                super.onIncomingCall(linphoneCall);
                Intent intent = new Intent(DialerActivity.this, MyIncomingCallActivity.class);
                intent.putExtra(LinphoneConstants.IntentKeys.CALLER_NUMBER, linphoneCall.getRemoteContact());
                Utils.startActivityOnMainThread(DialerActivity.this, intent);
            }
        });
    }
}
