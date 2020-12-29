package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddFriend extends AppCompatActivity {
    CircleImageView imageView;
    TextView textName, textPhone;
    Button submitButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        imageView = findViewById(R.id.profile_image_add);
        textName = findViewById(R.id.name_text_add);
        textPhone = findViewById(R.id.phone_text_add);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);

        // 취소버튼
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        // 추가버튼
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textName.getText().toString();
                String phone = textPhone.getText().toString();
                if(name.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                } else if(phone.length() == 0) {
                    Toast.makeText(getApplicationContext(), "전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });

        // 이미지 추가
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
    }
}