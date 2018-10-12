package com.ins.linphone.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ins.linphone.R;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {
    protected ListView listView;
    ContactAdapter contactAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contactlist, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) getView().findViewById(R.id.listContact);
        contactAdapter = new ContactAdapter(getActivity(), new ArrayList<ContactModel>());
        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((DialerContactActivity) getActivity()).onContactItemClicked(contactAdapter.getItem(position));
            }
        });
    }

    protected void setContactsList(List<ContactModel> contacts) {
        contactAdapter.setContactsList(contacts);
    }
}
