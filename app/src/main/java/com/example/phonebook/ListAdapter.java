package com.example.phonebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private ArrayList<CustomDTO> items = new ArrayList<>();

    // item의 개수
    @Override
    public int getCount() {
        return items.size();
    }

    // position에 있는 아이템 가져오기
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    // position 가져오기
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Item 표현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder;

        return null;
    }


}
