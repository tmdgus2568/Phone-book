package com.example.phonebook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PhonebookFragment extends Fragment {
    private static final int RESULT_OK = -1;
    ListView mListView;
    FloatingActionButton fButton;
    ArrayList<CustomDTO> phone_list;
    ListAdapter adapter;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phonebook, container, false);
        mListView = (ListView)v.findViewById(R.id.phone_list);

        // DB에서 가져온 전화번호 목록 출력
        bundle = getArguments();
        if(bundle != null) {
            showTable();
        }

        // 추가 버튼 onClick 이벤트 처리
        fButton = (FloatingActionButton)v.findViewById(R.id.fab_add);
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddFriend.class);
                startActivityForResult(intent, 1000);
            }
        });

        return v;
    }

    public void showTable() {
        // DB에서 새로 값을 불러옴
        ((MainActivity)MainActivity.mainContext).refreshTable();

        adapter = new ListAdapter();
        phone_list = bundle.getParcelableArrayList("friends");
        Log.d("showtable :::" , "showtable~!@~@~!@");
        // 데이터 입력
        for(CustomDTO item : phone_list) {
            adapter.addItem(item);
        }
        
        mListView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000:
                    Log.d("end", "case1000");
                    showTable();
                    break;
            }
        }
    }
}
