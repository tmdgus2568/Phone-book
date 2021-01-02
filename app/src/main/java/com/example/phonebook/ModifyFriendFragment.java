package com.example.phonebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ModifyFriendFragment extends Fragment {

    CircleImageView imageView;
    TextView textName, textPhone;
    Button submitButton, cancelButton;

    // imageview와 storage에 저장할 uri
    Uri imageUri;
    String img;

    // 파이어베이스 문서 경로
    String doc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_modify_friend, container, false);

        imageView = v.findViewById(R.id.profile_image_modify);
        textName = v.findViewById(R.id.name_text_modify);
        textPhone = v.findViewById(R.id.phone_text_modify);
        submitButton = v.findViewById(R.id.submitButton);
        cancelButton = v.findViewById(R.id.cancelButton);

        imageUri = null;
        img = null;

        Bundle bundle = getArguments();
        String args[] = bundle.getStringArray("data");

        img = args[0];
        textName.setText(args[1]);
        textPhone.setText(args[2]);
        doc = args[3];

        // 기본 이미지 설정
        StorageReference ref = FirebaseStorage.getInstance().getReference().child(img);
        if(ref != null) {
            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()) {
                        Glide.with(getContext()).load(task.getResult()).circleCrop().into(imageView);
                    } else {
                        Log.d("error", "불러오기실패");
                    }
                }
            });
        }

        // 취소버튼
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhonebookFragment phonebookFragment = new PhonebookFragment();
                ((MainActivity)getActivity()).replaceFragment(phonebookFragment);
            }
        });

        // 추가버튼
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textName.getText().toString();
                String phone = textPhone.getText().toString();
                if(name.length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                } else if(phone.length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    // firebase와 연동
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String, Object> friend = new HashMap<>();

                    friend.put("Name", name);
                    friend.put("Phone", phone);
                    if(imageUri!=null) {
                        clickUpload(v);
                    }
                    friend.put("Image", img);

                    // 저장할 문서 이름 생성
                    friend.put("Document", doc);

                    db.collection("friends").document(doc)
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
                    PhonebookFragment phonebookFragment = new PhonebookFragment();
                    ((MainActivity)getActivity()).replaceFragment(phonebookFragment);
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

        return v;
    }

    public void clickSelect(View view) {
        //사진을 선택할 수 있는 Gallery앱 실행
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        StorageReference imgRef = firebaseStorage.getReference(img);

        StorageReference ref = firebaseStorage.getReference().child(img);
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

        imgRef.putFile(imageUri);
    }
}
