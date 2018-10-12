package com.ins.linphone.linphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ins.linphone.utils.LogUtil;

public class KeepAliveHandler extends BroadcastReceiver {
    private static final String TAG = "KeepAliveHandler";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (LinphoneManager.getLcIfManagerNotDestroyOrNull() != null) {
            LinphoneManager.getLc().refreshRegisters();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LogUtil.d( "Cannot sleep for 2s");
            }
        }
    }
}
