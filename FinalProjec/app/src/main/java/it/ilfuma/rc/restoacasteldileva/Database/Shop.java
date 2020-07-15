package it.ilfuma.rc.restoacasteldileva.Database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Shop implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    public int categoryId;
    @ColumnInfo(name = "shopName")
    public String shopName;
    @ColumnInfo(name = "shopDescription")
    public String shopDescription;
    @ColumnInfo(name = "shopWebsite")
    public String shopWebsite;
    @ColumnInfo(name = "shopNumber1")
    public String shopNumber1;
    @ColumnInfo(name = "shopNumber2")
    public String shopNumber2;
    @ColumnInfo (name = "shopPosition")
    public String shopPosition;
    @ColumnInfo(name = "shopLogo")
    public String shopLogo;

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(categoryId);
        parcel.writeString(shopName);
        parcel.writeString(shopDescription);
        parcel.writeString(shopWebsite);
        parcel.writeString(shopNumber1);
        parcel.writeString(shopNumber2);
        parcel.writeString(shopPosition);
        parcel.writeString(shopLogo);
    }

    public Shop(){

    }

    private Shop(Parcel in){
        categoryId = in.readInt();
        shopName = in.readString();
        shopDescription = in.readString();
        shopWebsite = in.readString();
        shopNumber1 = in.readString();
        shopNumber2 = in.readString();
        shopPosition = in.readString();
        shopLogo = in.readString();
    }
}