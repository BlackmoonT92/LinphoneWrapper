package com.ins.linphone.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ins.linphone.R;

import java.util.List;

import im.dlg.dialer.DialpadFragment;

public abstract class DialerContactActivity extends BaseActivity implements DialpadFragment.Callback {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer_contact);
    }

    @Override
    public void ok(String formatted, String raw) {
        onPhoneNumberPicked(formatted, raw);
    }

    public void setContactsList(List<ContactModel> contactsList) {
        ContactsFragment contactsFragment = (ContactsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_contacts);
        if (contactsFragment != null) {
            contactsFragment.setContactsList(contactsList);
        }
    }

    public void setCurrentNumber(String number){
        ContactsFragment contactsFragment = (ContactsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_contacts);
        if (contactsFragment != null) {
            contactsFragment.setCurrentNumber(number);
        }
    }

    public abstract void onContactItemClicked(ContactModel contact);

    protected abstract void onPhoneNumberPicked(String formatted, String raw);
}
