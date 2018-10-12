package com.ins.linphone.callback;

import org.linphone.core.LinphoneCall;

public abstract class PhoneCallback {

    public void onIncomingCall(LinphoneCall linphoneCall) {}

    public void onOutgoingCallInit() {}

    public void onCallConnected() {}

    public void onCallEnd() {}

    public void OnCallReleased() {}

    public void onCallError() {}
}
