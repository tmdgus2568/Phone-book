package com.example.phonebook;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomDTO implements Parcelable {
    private String image;
    private String name;
    private String phone;

    public CustomDTO(String image, String name, String phone) {
        this.image = image;
        this.name = name;
        this.phone = phone;
    }

    protected CustomDTO(Parcel in) {
        image = in.readString();
        name = in.readString();
        phone = in.readString();
    }

    public static final Creator<CustomDTO> CREATOR = new Creator<CustomDTO>() {
        @Override
        public CustomDTO createFromParcel(Parcel in) {
            return new CustomDTO(in);
        }

        @Override
        public CustomDTO[] newArray(int size) {
            return new CustomDTO[size];
        }
    };

    public String getImage() { return image; }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setImage(String image) { this.image = image; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(phone);
    }
}
