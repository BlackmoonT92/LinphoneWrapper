package com.ins.linphoneexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.ins.linphone.LinphoneWrapper;
import com.ins.linphone.utils.LinphoneConstants;
import com.ins.linphone.utils.Utils;
import com.ins.linphone.callback.PhoneCallback;
import com.ins.linphone.ui.BaseActivity;

import org.linphone.core.LinphoneCall;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.dial_num) EditText mDialNum;
    @BindView(R.id.hang_up) Button mHangUp;
    @BindView(R.id.accept_call) Button mCallIn;
    @BindView(R.id.toggle_speaker) Button mToggleSpeaker;
    @BindView(R.id.toggle_mute) Button mToggleMute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.audio_call)
    public void audioCall() {
        String dialNum = mDialNum.getText().toString();
        //LinphoneWrapper.callTo(dialNum, false);

        Intent intent = new Intent(MainActivity.this, MyOutgoingCallActivity.class);
        intent.putExtra(LinphoneConstants.IntentKeys.CALLER_NUMBER, dialNum);
        Utils.startActivityOnMainThread(MainActivity.this, intent);
    }

    @OnClick(R.id.video_call)
    public void videoCall() {
        String dialNum = mDialNum.getText().toString();
        LinphoneWrapper.callTo(dialNum, true);
        startActivity(new Intent(MainActivity.this, VideoActivity.class));
    }

    @OnClick(R.id.hang_up)
    public void hangUp() {
        LinphoneWrapper.hangUp();
    }

    @OnClick(R.id.accept_call)
    public void acceptCall() {
        LinphoneWrapper.acceptCall();
        if (LinphoneWrapper.getVideoEnabled()) {
            startActivity(new Intent(MainActivity.this, VideoActivity.class));
        }
    }

    @OnClick(R.id.toggle_mute)
    public void toggleMute() {
        LinphoneWrapper.toggleMicro(!LinphoneWrapper.getLC().isMicMuted());
    }

    @OnClick(R.id.toggle_speaker)
    public void toggleSpeaker() {
        LinphoneWrapper.toggleSpeaker(!LinphoneWrapper.getLC().isSpeakerEnabled());
    }

    @Override
    protected void onResume() {
        super.onResume();
        addListener();
    }

    private void addListener() {
        LinphoneWrapper.addCallback(null, new PhoneCallback() {
            @Override
            public void onIncomingCall(LinphoneCall linphoneCall) {
                super.onIncomingCall(linphoneCall);
                //LinphoneWrapper.toggleSpeaker(true);
//                mCallIn.setVisibility(View.VISIBLE);
//                mHangUp.setVisibility(View.VISIBLE);
                Intent intent = new Intent(MainActivity.this, MyIncomingCallActivity.class);
                intent.putExtra(LinphoneConstants.IntentKeys.CALLER_NUMBER, linphoneCall.getRemoteContact());
                Utils.startActivityOnMainThread(MainActivity.this, intent);
            }

            @Override
            public void onOutgoingCallInit() {
                super.onOutgoingCallInit();
                mHangUp.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCallConnected() {
                super.onCallConnected();
                LinphoneWrapper.toggleSpeaker(LinphoneWrapper.getVideoEnabled());
                LinphoneWrapper.toggleMicro(false);
                mCallIn.setVisibility(View.GONE);
                mToggleSpeaker.setVisibility(View.VISIBLE);
                mToggleMute.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCallEnd() {
                super.onCallEnd();
                sendBroadcast(new Intent(VideoActivity.RECEIVE_FINISH_VIDEO_ACTIVITY));
                mCallIn.setVisibility(View.GONE);
                mHangUp.setVisibility(View.GONE);
                mToggleMute.setVisibility(View.GONE);
                mToggleSpeaker.setVisibility(View.GONE);
//                LinphoneWrapper.logout(getApplicationContext());
//                Utils.startActivityOnMainThread(MainActivity.this, new Intent(MainActivity.this, LoginActivity.class));
            }

            @Override
            public void OnCallReleased() {
                super.OnCallReleased();
            }

            @Override
            public void onCallError() {
                super.onCallError();
            }
        });
    }
}
