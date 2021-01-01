package com.example.phonebook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener{

    public static BottomSheetDialog getInstance() { return new BottomSheetDialog(); }

    private LinearLayout emailLo;
    private LinearLayout cloudLo;
    
    // 받아오는 값
    Bundle bundle;
    int position;
    String data[];

    // 주는곳
    PhonebookFragment phonebookFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_dialog, container,false);
        emailLo = (LinearLayout) view.findViewById(R.id.deleteRow);
        cloudLo = (LinearLayout) view.findViewById(R.id.modifyRow);
        
        bundle = getArguments();
        position = bundle.getInt("position");
        data = bundle.getStringArray("data");

        Log.d("로그", Integer.toString(position));

        emailLo.setOnClickListener(this);
        cloudLo.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.deleteRow:
                Toast.makeText(getContext(),"삭제",Toast.LENGTH_SHORT).show();

                bundle = new Bundle();
                bundle.putInt("res", 1);
                bundle.putInt("position", position);
                bundle.putStringArray("data", data);

                phonebookFragment = new PhonebookFragment();
                phonebookFragment.setArguments(bundle);

                ((MainActivity)getActivity()).replaceFragment(phonebookFragment);

                break;
            case R.id.modifyRow:
                Toast.makeText(getContext(),"수정",Toast.LENGTH_SHORT).show();

                bundle = new Bundle();
                bundle.putInt("res", 2);
                bundle.putInt("position", position);
                bundle.putStringArray("data", data);

                phonebookFragment = new PhonebookFragment();
                phonebookFragment.setArguments(bundle);

                ((MainActivity)getActivity()).replaceFragment(phonebookFragment);

                break;
        }
        dismiss();
    }
}
