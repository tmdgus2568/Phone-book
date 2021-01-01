package com.example.phonebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class PhonebookFragment extends Fragment {
    private static final int RESULT_OK = -1;

    ListView mListView;
    FloatingActionButton fButton;
    ArrayList<CustomDTO> phone_list;
    ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phonebook, container, false);
        mListView = (ListView)v.findViewById(R.id.phone_list);

        refreshTable();

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000:
                    refreshFragment();
                    break;
            }
        }
    }

    public void refreshFragment() {
        Log.d("ref", "refresh");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    // Firebase에서 리스트를 불러와서 phone_table에 업데이트
    public void refreshTable() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("friends")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        phone_list = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                HashMap<String, Object> map = (HashMap)document.getData();
                                CustomDTO item = new CustomDTO(map.get("Image").toString(), map.get("Name").toString(), map.get("Phone").toString());
                                phone_list.add(item);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        viewTable();
                        Log.d("Loading complete", "comp");
                    }
                });

    }

    public void viewTable() {
        adapter = new ListAdapter();
        for(CustomDTO item : phone_list) {
            adapter.addItem(item);
        }
        mListView.setAdapter(adapter);
    }
}
