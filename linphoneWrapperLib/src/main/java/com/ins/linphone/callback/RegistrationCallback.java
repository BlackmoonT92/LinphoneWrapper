package com.ins.linphone.callback;

public abstract class RegistrationCallback {
    public void onRegistrationNone() {}

    public void OnRegistrationInProgress() {}

    public void OnRegistrationOk() {}

    public void OnRegistrationCleared() {}

    public void OnRegistrationFailed() {}
}
