package com.example.phonebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.AdapterView;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class PhonebookFragment extends Fragment {
    private static final int RESULT_OK = -1;

    ListView mListView;
    FloatingActionButton fButton;
    ArrayList<CustomDTO> phone_list;
    ListAdapter adapter;

    int responseCode;
    int responsePosition;
    String responseData[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phonebook, container, false);
        mListView = (ListView)v.findViewById(R.id.phone_list);

        // Bottom sheet dialog에서 받은 응답에 따라 처리 (1 : 삭제, 2 : 수정)
        Bundle args = getArguments();
        Log.d("폰북", "시작");

        if(args != null) {
            responseCode = args.getInt("res");
            responsePosition = args.getInt("position");
            responseData = args.getStringArray("data");

            Log.d("응답", Integer.toString(responseCode));

            if(responseCode == 1) {
                // 응답코드가 1이면 삭제 dialog를 띄움
                new AlertDialog.Builder(getContext())
                        .setTitle("전화번호 삭제")
                        .setMessage("정말로 삭제하시겠습니까?")
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // 아무일도 안 일어남
                                refreshFragment();
                            }
                        })
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // 이 버튼 클릭시 삭제 진행

                                // 1. 이미지 삭제
                                if(!responseData[0].equals("profile_image/basic_profile.png")) {
                                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                                    StorageReference ref = firebaseStorage.getReference().child(responseData[0]);

                                    ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // File deleted successfully
                                            Log.d("삭제", "성공");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Uh-oh, an error occurred!
                                            Log.d("삭제", "실패");
                                        }
                                    });
                                }

                                // 2. DB 삭제
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("friends").document(responseData[3])
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TAG", "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error deleting document", e);
                                            }
                                        });

                                // 3. 새로고침
                                refreshFragment();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        } else {
            // 테이블 초기화
            refreshTable();
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

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Click", adapter.getItem(position).getName() + adapter.getItem(position).getPhone());

                Bundle bundle = new Bundle();
                bundle.putString("Name", adapter.getItem(position).getName());
                bundle.putString("Phone", adapter.getItem(position).getPhone());

                CallFragment callFragment = new CallFragment();
                callFragment.setArguments(bundle);

                ((MainActivity)getActivity()).replaceFragment(callFragment);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Long Click", "long click");

                CustomDTO item = adapter.getItem(position);
                String arr[] = {item.getImage(), item.getName(), item.getPhone(), item.getDocument()};

                Bundle bundle = new Bundle();

                bundle.putInt("position", position);
                bundle.putStringArray("data", arr);

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.setArguments(bundle);

                bottomSheetDialog.show(((MainActivity)getActivity()).getSupportFragmentManager(), "bottomSheet");

                return true;
            }
        });

        return v;
    }

    // 추가버튼 클릭 이후 처리
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

    // 현재 프래그먼트 새로고침
    public void refreshFragment() {
        Log.d("ref", "refresh");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(this);
        ft.add(R.id.frameLayout, new PhonebookFragment()).commit();
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
                                CustomDTO item = new CustomDTO(map.get("Image").toString(), map.get("Name").toString(), map.get("Phone").toString(), map.get("Document").toString());
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

    // 업데이트된 phone_list를 listview에 표시 (adapter 이용)
    public void viewTable() {
        adapter = new ListAdapter();
        for(CustomDTO item : phone_list) {
            adapter.addItem(item);
        }
        mListView.setAdapter(adapter);
    }

    // position에 위치한 list item 삭제
    public void deleteItem(int position) {
        phone_list.remove(position);
        adapter.notifyDataSetChanged();
    }
}
