package com.example.admin.redemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wl on 2016/10/11.
 */
public class mBaseAdapter extends BaseAdapter {

    private ArrayList<String> sList;
    private Context context;
    private static HashMap<Integer, Boolean> isSelected;
    private static HashMap<Integer, Boolean> isVisible;

    public mBaseAdapter(ArrayList<String> sList, Context context) {
        this.sList = sList;
        this.context = context;
        isSelected = new HashMap<>();
        isVisible = new HashMap<>();
        initDate();
    }

    private void initDate() {
        for (int i = 0; i < sList.size(); i++) {
            isSelected.put(i, false);
            isVisible.put(i, true);
        }
    }

    @Override
    public int getCount() {
        return sList.size();
    }

    @Override
    public Object getItem(int position) {
        return sList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        mBaseAdapter.isSelected = isSelected;
    }

    public static HashMap<Integer, Boolean> getIsVisible() {
        return isVisible;
    }

    public static void setIsVisible(HashMap<Integer, Boolean> isVisible) {
        mBaseAdapter.isVisible = isVisible;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.m_adapter, null);
            viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.mCheckBox);
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.mTextView);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (isVisible.get(position)) {
            viewHolder.mImageView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mImageView.setVisibility(View.INVISIBLE);
        }
        viewHolder.mCheckBox.setChecked(isSelected.get(position));
        viewHolder.mTextView.setText(sList.get(position));
        return convertView;
    }

    public class ViewHolder {
        public CheckBox mCheckBox;
        public TextView mTextView;
        public ImageView mImageView;
    }
}

