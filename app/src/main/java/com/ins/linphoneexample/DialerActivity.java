package com.ins.linphoneexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ins.linphone.LinphoneWrapper;
import com.ins.linphone.callback.PhoneCallback;
import com.ins.linphone.ui.ContactModel;
import com.ins.linphone.ui.DialerContactActivity;
import com.ins.linphone.utils.LinphoneConstants;
import com.ins.linphone.utils.Utils;

import org.linphone.core.LinphoneCall;

import java.util.ArrayList;
import java.util.List;

public class DialerActivity extends DialerContactActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<ContactModel> contactModelList = new ArrayList<ContactModel>();
        for (int i = 0; i < 100; i++) {
            contactModelList.add(new ContactModel("Contact " + i, "2340" + i));
        }
        setContactsList(contactModelList);
    }

    @Override
    public void onContactItemClicked(ContactModel contact) {
        if (contact != null) {
            callNumber(contact.getPhoneNumber());
        }
    }

    @Override
    protected void onPhoneNumberPicked(String formatted, String raw) {
        callNumber(raw);
    }

    private void callNumber(String number) {
        Intent intentCall = new Intent(this, MyOutgoingCallActivity.class);
        intentCall.putExtra(LinphoneConstants.IntentKeys.CALLER_NUMBER, number);
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
