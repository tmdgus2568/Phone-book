package com.example.phonebook;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomDTO implements Parcelable {
    private String image;
    private String name;
    private String phone;
    private String document;

    public CustomDTO(String image, String name, String phone, String document) {
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.document = document;
    }

    protected CustomDTO(Parcel in) {
        image = in.readString();
        name = in.readString();
        phone = in.readString();
        document = in.readString();
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

    public String getDocument() { return document; }

    public void setImage(String image) { this.image = image; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDocument(String document) { this.document = document; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(document);
    }
}
