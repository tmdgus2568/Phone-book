package com.example.phonebook;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

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
    ImageView delete_btn;
    ArrayList<SearchItem> phone_list;
    BackgroundTask task;
    View v;
    ListView search_list;
    SearchAdapter adapter;
    int value;

    Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_call, container, false);

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
        delete_btn = v.findViewById(R.id.delete_btn);
        search_list = v.findViewById(R.id.search_list);

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
        delete_btn.setOnClickListener(this::onClick);

        adapter = new SearchAdapter();
        phone_list = new ArrayList<>();

        search_list.setAdapter(adapter);



        // 전화번호부에서 클릭시 이동
        bundle = getArguments();
        if(bundle != null) {
            // 데이터 수신
            String name = bundle.getString("Name");
            String phone = bundle.getString("Phone");
            Log.d("전송완료", name+phone);
            callEdit.setText(phone);


        }

        task = new BackgroundTask();
        task.execute();

        callEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String number = callEdit.getText().toString();
                search(number);
//                System.out.println("here~~~~~");
            }
        });



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
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:"+callEdit.getText().toString()));
                startActivity(dialIntent);
                break;
            case R.id.delete_btn:
                callEdit.setText(callEdit.getText().toString().replaceFirst(".$",""));

        }
    }

    class BackgroundTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            final boolean[] end = {false};

            db.collection("friends")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("TAG", document.getId() + " => " + document.getData());
                                    HashMap<String, Object> map = (HashMap)document.getData();
                                    SearchItem items = new SearchItem(map.get("Name").toString(), map.get("Phone").toString());
                                    phone_list.add(items);
                                    System.out.println("here~~~~");

                                }
                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());
                            }

                            Log.d("Loading complete", "comp");
//                            for(int i=0;i<phone_list.size();i++){
//                                adapter.addItem(phone_list.get(i).getName(), phone_list.get(i).getNumber());
//                                System.out.println(phone_list.get(i).getName()+" and " +phone_list.get(i).getNumber());
//                            }
//                            search_list.setAdapter(adapter);
                            search(callEdit.getText().toString());


                        }
                    });

            return true;
        }

    }

    public void search(String number){
//        phone_list.clear();
        adapter = new SearchAdapter();
        search_list.setAdapter(adapter);

        for(int i=0;i<phone_list.size();i++){
            if(phone_list.get(i).getNumber().contains(number) && !number.isEmpty()){
                adapter.addItem(phone_list.get(i).getName(), phone_list.get(i).getNumber());
            }
        }
        adapter.notifyDataSetChanged();
    }


}
