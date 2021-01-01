package com.example.phonebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static Context mainContext;

    BottomNavigationView bottomNavigationView;
    ArrayList<CustomDTO> phone_table = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new CallFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_call:
                        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new CallFragment()).commit();
                        break;
                    case R.id.action_phonebook:
                        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new PhonebookFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }

    public void replaceFragment(Fragment frag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, frag).commit();
    }
}