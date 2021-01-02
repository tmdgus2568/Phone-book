package com.example.phonebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {
    TextView textViewName;
    TextView textViewNumber;
    ArrayList<SearchItem> Items = new ArrayList<>();

    public SearchAdapter(){

    }
    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public Object getItem(int position) {
        return Items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.call_list_item, parent, false);
        }
        textViewName = convertView.findViewById(R.id.call_name);
        textViewNumber = convertView.findViewById(R.id.call_number);
        textViewName.setText(Items.get(position).getName());
        textViewNumber.setText(Items.get(position).getNumber());

        return convertView;
    }
    public void addItem(String name, String number) {
        SearchItem item = new SearchItem(name, number);

        item.setName(name);
        item.setNumber(number);

        Items.add(item);
    }
}
