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

import java.util.ArrayList;

public class PhonebookFragment extends Fragment {
    ListView mListView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phonebook, container, false);

        mListView = (ListView)v.findViewById(R.id.phone_list);
        ListAdapter adapter = new ListAdapter();

        // 아이템 넣는 부분
        String names[] = {"승현001", "승현002", "승현003", "승현004", "승현005", "승현006", "승현007", "승현008"};
        String phones[] = {"010-1111-1111", "010-2222-2222", "010-3333-3333", "010-4444-4444", "010-5555-5555", "010-6666-6666", "010-7777-7777", "010-8888-8888"};
        for(int i = 0; i < 8; i++) {
            adapter.addItem(names[i], phones[i]);
        }

        mListView.setAdapter(adapter);
        return v;
    }


}
