package com.example.phonebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

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


}