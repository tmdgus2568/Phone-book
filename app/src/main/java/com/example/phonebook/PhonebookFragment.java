package com.example.phonebook;

import android.os.Bundle;
import android.widget.ListView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PhonebookFragment extends Fragment {
    ListView mListView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phonebook, container, false);

        mListView = (ListView)v.findViewById(R.id.phone_list);
        ListAdapter adapter = new ListAdapter();

        // 아이템 넣는 부분
        adapter.addItem("승현", "010-9422-5397");
        adapter.addItem("승현22", "010-1234-5678");

        mListView.setAdapter(adapter);
        return v;
    }


}
