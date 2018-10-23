package com.ins.linphone.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.ins.linphone.LinphoneWrapper;
import com.ins.linphone.R;
import com.ins.linphone.linphone.LinphoneManager;
import com.ins.linphone.linphone.PhoneBean;

import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneAuthInfo;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCallParams;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCoreException;
import org.linphone.core.LinphoneCoreFactory;
import org.linphone.core.LinphoneProxyConfig;
import org.linphone.core.LpConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.content.Context.AUDIO_SERVICE;

public class LinphoneUtils {
    private static final String TAG = "LinphoneUtils";
    private static volatile LinphoneUtils sLinphoneUtils;
    private static int ringingVolume = 50;
    private LinphoneCore mLinphoneCore = null;
    private static MediaPlayer mMediaPlayer = null;

    public static LinphoneUtils getInstance() {
        if (sLinphoneUtils == null) {
            synchronized (LinphoneUtils.class) {
                if (sLinphoneUtils == null) {
                    sLinphoneUtils = new LinphoneUtils();
                }
            }
        }
        return sLinphoneUtils;
    }

    private LinphoneUtils() {
        mLinphoneCore = LinphoneManager.getLc();
        mLinphoneCore.enableEchoCancellation(true);
        mLinphoneCore.enableEchoLimiter(true);
    }

    public static void setVolume(int percent) {
        ringingVolume = percent;
    }

    public void registerUserAuth(String name, String password, String host) throws LinphoneCoreException {
        LogUtil.d( "registerUserAuth name = " + name);
        LogUtil.d( "registerUserAuth pw = " + password);
        LogUtil.d( "registerUserAuth host = " + host);
        String identify = "sip:" + name + "@" + host;
        String proxy = "sip:" + host;
        LinphoneAddress proxyAddr = LinphoneCoreFactory.instance().createLinphoneAddress(proxy);
        LinphoneAddress identifyAddr = LinphoneCoreFactory.instance().createLinphoneAddress(identify);
        LinphoneAuthInfo authInfo = LinphoneCoreFactory.instance().createAuthInfo(name, null, password,
                null, null, host);
        LinphoneProxyConfig prxCfg = mLinphoneCore.createProxyConfig(identifyAddr.asString(),
                proxyAddr.asStringUriOnly(), proxyAddr.asStringUriOnly(), true);
        prxCfg.enableAvpf(false);
        prxCfg.setAvpfRRInterval(0);
        prxCfg.enableQualityReporting(false);
        prxCfg.setQualityReportingCollector(null);
        prxCfg.setQualityReportingInterval(0);
        prxCfg.enableRegister(true);
        mLinphoneCore.addProxyConfig(prxCfg);
        mLinphoneCore.addAuthInfo(authInfo);
        mLinphoneCore.setDefaultProxyConfig(prxCfg);
    }

    public void unRegisterUserAuth(){
        if(mLinphoneCore == null) return;
        for (LinphoneProxyConfig proxyConfig : mLinphoneCore.getProxyConfigList()) {
            if (proxyConfig != null) {
                proxyConfig.edit();
                proxyConfig.setExpires(0);
                proxyConfig.done();
                mLinphoneCore.refreshRegisters();
            }
        }
    }

    public LinphoneCall startSingleCallingTo(PhoneBean bean, boolean isVideoCall) {
        LinphoneAddress address;
        LinphoneCall call = null;
        try {
            address = mLinphoneCore.interpretUrl(bean.getUserName() + "@" + bean.getHost());
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
            return null;
        }
        address.setDisplayName(bean.getDisplayName());
        LinphoneCallParams params = mLinphoneCore.createCallParams(null);
        if (isVideoCall) {
            params.setVideoEnabled(true);
            params.enableLowBandwidth(false);
        } else {
            params.setVideoEnabled(false);
        }
        try {
            call = mLinphoneCore.inviteAddressWithParams(address, params);
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
        return call;
    }

    public void hangUp() {
        LinphoneCall currentCall = mLinphoneCore.getCurrentCall();
        if (currentCall != null) {
            mLinphoneCore.terminateCall(currentCall);
        } else if (mLinphoneCore.isInConference()) {
            mLinphoneCore.terminateConference();
        } else {
            mLinphoneCore.terminateAllCalls();
        }
    }

    public void toggleMicro(boolean isMicMuted) {
        mLinphoneCore.muteMic(isMicMuted);
    }

     public void toggleSpeaker(boolean isSpeakerEnabled) {
         mLinphoneCore.enableSpeaker(isSpeakerEnabled);
     }

    public static void copyIfNotExist(Context context, int resourceId, String target) throws IOException {
        File fileToCopy = new File(target);
        if (!fileToCopy.exists()) {
            copyFromPackage(context, resourceId, fileToCopy.getName());
        }
    }

    public static void copyFromPackage(Context context, int resourceId, String target) throws IOException {
        FileOutputStream outputStream = context.openFileOutput(target, 0);
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        int readByte;
        byte[] buff = new byte[8048];
        while ((readByte = inputStream.read(buff)) != -1) {
            outputStream.write(buff, 0, readByte);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public static LpConfig getConfig(Context context) {
        LinphoneCore lc = getLc();
        if (lc != null) {
            return lc.getConfig();
        }

        if (LinphoneManager.isInstanceiated()) {
            org.linphone.mediastream.Log.w("LinphoneManager not instanciated yet...");
            return LinphoneCoreFactory.instance().createLpConfig(context.getFilesDir().getAbsolutePath() + "/.linphonerc");
        }

        return LinphoneCoreFactory.instance().createLpConfig(LinphoneManager.getInstance().mLinphoneConfigFile);
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static LinphoneCore getLc() {
        if (!LinphoneManager.isInstanceiated()) {
            return null;
        }
        return LinphoneManager.getLcIfManagerNotDestroyOrNull();
    }

    public static MediaPlayer playRingingSound(Context context) {
        LinphoneWrapper.toggleSpeaker(true);
        AudioManager audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (maxVolume * ringingVolume / 100), AudioManager.FLAG_PLAY_SOUND);
        int result = audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            return null;
        }
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ring);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(true);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
            }
        });
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
        mMediaPlayer = mediaPlayer;
        return mediaPlayer;
    }

    public static void stopRining() {
        stopPlayingSound(mMediaPlayer);
    }

    public static void stopPlayingSound(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
