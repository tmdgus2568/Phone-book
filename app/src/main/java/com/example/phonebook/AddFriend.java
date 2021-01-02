package com.example.phonebook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddFriend extends AppCompatActivity {
    CircleImageView imageView;
    TextView textName, textPhone;
    Button submitButton, cancelButton;

    // imageview와 storage에 저장할 uri
    Uri imageUri;
    String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        imageView = findViewById(R.id.profile_image_modify);
        textName = findViewById(R.id.name_text_modify);
        textPhone = findViewById(R.id.phone_text_modify);
        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);

        imageUri = null;
        img = null;

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
                    // firebase와 연동
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String, Object> friend = new HashMap<>();

                    friend.put("Name", name);
                    friend.put("Phone", phone);
                    if(imageUri!=null) {
                        clickUpload(v);
                        friend.put("Image", img);
                    } else {
                        friend.put("Image", "profile_image/basic_profile.png");
                    }

                    // 저장할 문서 이름 생성
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                    String docuName = sdf.format(new Date())+"";
                    friend.put("Document", docuName);

                    db.collection("friends").document(docuName)
                            .set(friend)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot added with : " + "test");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error adding document",e);
                                }
                            });

                    // 결과값 반환
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    finish();
                }
            }
        });

        // 이미지 추가
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSelect(v);
            }
        });
    }

    public void clickSelect(View view) {
        //사진을 선탣할 수 있는 Gallery앱 실행
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    //선택한 이미지의 경로 얻어오기
                    imageUri= data.getData();
                    Glide.with(this).load(imageUri).circleCrop().into(imageView);
                }
                break;
        }
    }

    public void clickUpload(View view) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String filename = sdf.format(new Date())+".png";

        img = "profile_image/"+filename;

        StorageReference imgRef = firebaseStorage.getReference(img);

        imgRef.putFile(imageUri);
    }
}