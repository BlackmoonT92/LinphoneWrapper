# LinphoneWrapper

## Import
Linphone Android SDK at [Linphone offical website](http://www.linphone.org/technical-corner/liblinphone/downloads). 

Then
copy LinphoneWrapper aar and Linphone aar to your project's `libs` folder, and import them in your app buid.gradle as below:

```groovy
android {
	...
	repositories {
	    flatDir {
	        dirs 'libs' //this way we can find the .aar file in libs folder
	    }
	}
}


```

## Usage
### 1. Init LinphoneService

```java
// Start service
LinphoneWrapper.startService(mContext);
// Add callback
LinphoneWrapper.addCallback(null, new PhoneCallback() {
         @Override
         public void onIncomingCall(LinphoneCall linphoneCall) {
             super.onIncomingCall(linphoneCall);
         }         
         @Override
         public void onOutgoingCallInit() {
             super.onOutgoingCallInit();

        @Override
         public void onCallConnected() {
             super.onCallConnected();
         }
         @Override
         public void onCallEnd() {
             super.onCallEnd();
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
```

You can add registrationCallback and phoneCallback in different place in your project, this depending on your logic.

### 2. Login

```java
// Configure sip account
// Register to sip server
LinphoneWrapper.login("1234", "password", "192.168.100.31");
```

### 3. Manage the voice call

```java
// Make a call
LinphoneWrapper.callTo("1001");
// Hang up the current call
LinphoneWrapper.hangUp();
// Answer the current call
LinphoneWrapper.acceptCall();
// Toggle the mute function
LinphoneWrapper.toggleMicro(!LinphoneWrapper.getLC().isMicMuted());
// Toggle the handsfree function
LinphoneWrapper.toggleSpeaker(!LinphoneWrapper.getLC().isSpeakerEnabled());
```

### 4. Manage the video call

Your video call activity or fragment may like below:

```java
public class VideoActivity extends AppCompatActivity {
    @BindView(R.id.video_rendering) SurfaceView mRenderingView;
    @BindView(R.id.video_preview) SurfaceView mPreviewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        LinphoneWrapper.setAndroidVideoWindow(new SurfaceView[]{mRenderingView}, new SurfaceView[]{mPreviewView});
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinphoneWrapper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LinphoneWrapper.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LinphoneWrapper.onDestroy();
    }

    @OnClick(R.id.video_hang)
    public void hang() {
        LinphoneWrapper.hangUp();
        finish();
    }
}
```

That's all, Enjoy it!
