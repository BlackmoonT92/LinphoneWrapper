package com.ins.linphoneexample;

import android.content.Intent;
import com.ins.linphone.utils.LinphoneConstants;
import im.dlg.dialer.DialpadActivity;

public class DialerActivity extends DialpadActivity {

    @Override
    public void ok(String formatted, String raw) {
        super.ok(formatted, raw);
        Intent intentCall = new Intent(this, MyOutgoingCallActivity.class);
        intentCall.putExtra(LinphoneConstants.IntentKeys.CALLER_NUMBER, raw);
        startActivity(intentCall);
    }
}
