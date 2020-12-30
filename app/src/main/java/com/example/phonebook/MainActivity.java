package com.example.phonebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Context mainContext;

    BottomNavigationView bottomNavigationView;
    ArrayList<CustomDTO> phone_table = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation);

        // 처음 앱이 실행되었을 때 전화번호목록을 불러옴
        refreshTable();

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new CallFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_call:
                        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new CallFragment()).commit();
                        break;
                    case R.id.action_phonebook:
                        Fragment phonebookFragment = new PhonebookFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, phonebookFragment).commit();

                        // fragment에 전화번호 목록 값 전달
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("friends", (ArrayList<? extends Parcelable>) phone_table);
                        phonebookFragment.setArguments(bundle);

                        break;
                }
                return true;
            }
        });

        mainContext = this;
    }

    // Firebase에서 리스트를 불러와서 phone_table에 업데이트
    public void refreshTable() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("friends")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        phone_table = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                HashMap<String, Object> map = (HashMap)document.getData();
                                CustomDTO item = new CustomDTO(map.get("Image").toString(), map.get("Name").toString(), map.get("Phone").toString());
                                phone_table.add(item);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


}