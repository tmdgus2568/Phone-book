package com.example.phonebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
    public Object getItem(int position) {
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
        CircleImageView pb_img = (CircleImageView) convertView.findViewById(R.id.profile_image_add) ;
        TextView pb_name = (TextView) convertView.findViewById(R.id.name) ;
        TextView pb_phone = (TextView) convertView.findViewById(R.id.phone) ;

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        CustomDTO myItem = (CustomDTO) getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */

//        이미지 넣는거 나중에 할게여..
//        pb_img.setImageDrawable(myItem.getIcon());
        pb_name.setText(myItem.getName());
        pb_phone.setText(myItem.getPhone());

        /* 위젯에 대한 이벤트리스너  */

        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String image, String name, String phone) {
        CustomDTO mItem = new CustomDTO(image, name, phone);

        /* mItems에 MyItem을 추가한다. */
        items.add(mItem);
    }

    /* 객체로 아이템 추가 */
    public void addItem(CustomDTO item) {
        items.add(item);
    }
}
