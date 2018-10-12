package com.ins.linphone.utils;

import android.os.CountDownTimer;

public abstract class CountUpTimer extends CountDownTimer {
    private static final long DURATION = Long.MAX_VALUE;

    protected CountUpTimer(int interval) {
        super(DURATION, interval);
    }

    public abstract void onTick(int second);

    @Override
    public void onTick(long msUntilFinished) {
        int second = (int) ((DURATION - msUntilFinished) / 1000);
        onTick(second);
    }

    @Override
    public void onFinish() {
    }
}