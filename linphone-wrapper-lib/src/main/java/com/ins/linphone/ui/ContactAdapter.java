package com.ins.linphone.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.linphone.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<ContactModel> {

    private List<ContactModel> dataSet;
    Context mContext;
    private int lastPosition = -1;

    public void setContactsList(List<ContactModel> contacts) {
        dataSet.clear();
        dataSet.addAll(contacts);
        notifyDataSetChanged();
    }

    // View lookup cache
    private static class ViewHolder {
        TextView tvContactName;
        TextView tvContactNumber;
        ImageView ivAvatar;
    }

    public ContactAdapter(Context context, ArrayList<ContactModel> data) {
        super(context, R.layout.item_contact, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Nullable
    @Override
    public ContactModel getItem(int position) {
        return dataSet.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        ContactModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_contact, parent, false);
            viewHolder.tvContactName = (TextView) convertView.findViewById(R.id.tvContactName);
            viewHolder.tvContactNumber = (TextView) convertView.findViewById(R.id.tvContactNumber);
            viewHolder.ivAvatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.tvContactName.setText(dataModel.getName());
        viewHolder.tvContactNumber.setText(dataModel.getPhoneNumber());
        // Return the completed view to render on screen
        return convertView;
    }

}
