package com.example.phonebook;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapter extends BaseAdapter {
    private ArrayList<CustomDTO> items = new ArrayList<>();

    // item의 개수
    @Override
    public int getCount() {
        return items.size();
    }

    // position에 있는 아이템 가져오기
    @Override
    public CustomDTO getItem(int position) {
        return items.get(position);
    }

    // position 가져오기
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Item 표현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        // 'list_item' Layout을 inflate하여 convertView 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        /* 'list_item'에 정의된 위젯에 대한 참조 획득 */
        CircleImageView pb_img = (CircleImageView) convertView.findViewById(R.id.profile_image);
        TextView pb_name = (TextView) convertView.findViewById(R.id.name) ;
        TextView pb_phone = (TextView) convertView.findViewById(R.id.phone) ;

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        CustomDTO myItem = (CustomDTO) getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        setImage(myItem, convertView, pb_img);
        pb_name.setText(myItem.getName());
        pb_phone.setText(myItem.getPhone());

        /* 위젯에 대한 이벤트리스너  */

        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String image, String name, String phone, String document) {
        CustomDTO mItem = new CustomDTO(image, name, phone, document);

        /* mItems에 MyItem을 추가한다. */
        items.add(mItem);
    }

    /* 객체로 아이템 추가 */
    public void addItem(CustomDTO item) {
        items.add(item);
    }

    public void setImage(CustomDTO myItem, View convertView, CircleImageView pb_img) {
        StorageReference ref = FirebaseStorage.getInstance().getReference().child(myItem.getImage());
        if(ref != null) {
            View finalConvertView = convertView;
            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()) {
                        Glide.with(finalConvertView.getContext()).load(task.getResult()).into(pb_img);
                    } else {
                        Log.d("error", "불러오기실패");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setImage(myItem, convertView, pb_img);
                            }
                        }, 500);
                    }
                }
            });
        }
    }
}
