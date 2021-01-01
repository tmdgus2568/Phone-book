package com.example.phonebook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CallFragment extends Fragment {
    EditText callEdit;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView imageView10;
    ImageView imageView11;
    ImageView imageView12;
    ImageView call_btn;

    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_call, container, false);

        // 전화번호부에서 클릭시 이동
        bundle = getArguments();
        if(bundle != null) {
            // 데이터 수신
            String name = bundle.getString("Name");
            String phone = bundle.getString("Phone");
            Log.d("전송완료", name+phone);
        }

        callEdit = v.findViewById(R.id.callEdit);
        imageView1 = v.findViewById(R.id.imageView);
        imageView2 = v.findViewById(R.id.imageView2);
        imageView3 = v.findViewById(R.id.imageView3);
        imageView4 = v.findViewById(R.id.imageView4);
        imageView5 = v.findViewById(R.id.imageView5);
        imageView6 = v.findViewById(R.id.imageView6);
        imageView7 = v.findViewById(R.id.imageView7);
        imageView8 = v.findViewById(R.id.imageView8);
        imageView9 = v.findViewById(R.id.imageView9);
        imageView10 = v.findViewById(R.id.imageView10);
        imageView11 = v.findViewById(R.id.imageView11);
        imageView12 = v.findViewById(R.id.imageView12);
        call_btn = v.findViewById(R.id.call_btn);

        imageView1.setOnClickListener(this::onClick);
        imageView2.setOnClickListener(this::onClick);
        imageView3.setOnClickListener(this::onClick);
        imageView4.setOnClickListener(this::onClick);
        imageView5.setOnClickListener(this::onClick);
        imageView6.setOnClickListener(this::onClick);
        imageView7.setOnClickListener(this::onClick);
        imageView8.setOnClickListener(this::onClick);
        imageView9.setOnClickListener(this::onClick);
        imageView10.setOnClickListener(this::onClick);
        imageView11.setOnClickListener(this::onClick);
        imageView12.setOnClickListener(this::onClick);
        call_btn.setOnClickListener(this::onClick);


        return v;
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.imageView:
                callEdit.setText(callEdit.getText().toString()+"1");
                break;
            case R.id.imageView2:
                callEdit.setText(callEdit.getText().toString()+"2");
                break;
            case R.id.imageView3:
                callEdit.setText(callEdit.getText().toString()+"3");
                break;
            case R.id.imageView4:
                callEdit.setText(callEdit.getText().toString()+"4");
                break;
            case R.id.imageView5:
                callEdit.setText(callEdit.getText().toString()+"5");
                break;
            case R.id.imageView6:
                callEdit.setText(callEdit.getText().toString()+"6");
                break;
            case R.id.imageView7:
                callEdit.setText(callEdit.getText().toString()+"7");
                break;
            case R.id.imageView8:
                callEdit.setText(callEdit.getText().toString()+"8");
                break;
            case R.id.imageView9:
                callEdit.setText(callEdit.getText().toString()+"9");
                break;
            case R.id.imageView10:
                callEdit.setText(callEdit.getText().toString()+"*");
                break;
            case R.id.imageView11:
                callEdit.setText(callEdit.getText().toString()+"0");
                break;
            case R.id.imageView12:
                callEdit.setText(callEdit.getText().toString()+"#");
                break;
            case R.id.call_btn:
//                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:01033712568")));
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:"+callEdit.getText().toString()));
                startActivity(dialIntent);
                break;

        }
    }


}
