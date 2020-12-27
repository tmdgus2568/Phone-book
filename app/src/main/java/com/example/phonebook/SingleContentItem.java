package com.example.phonebook;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingleContentItem extends LinearLayout {
    CircleImageView imageView;
    TextView textName, textPhone;

    public SingleContentItem(Context context) {
        super(context);
        init(context);
    }

    public SingleContentItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_item, this. true);

        imageView = findViewById(R.id.profile_image);
        textName = findViewById(R.id.name);
        textPhone = findViewById(R.id.phone);
    }
}
