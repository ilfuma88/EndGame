package it.ilfuma.rc.restoacasteldileva.Database;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;
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
    @ColumnInfo(name = "discount10")
    public String discount10;
    @ColumnInfo(name = "condition10" )
    public String condition10;
    @ColumnInfo(name = "complimentaryService")
    public String complimentaryService;
    @ColumnInfo(name = "conditionComplimentary")
    public String conditionComplimentary;
    @ColumnInfo(name = "specialPackage")
    public String specialPackage;
    @ColumnInfo(name = "conditionSpecial")
    public String conditionSpecial;

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
        parcel.writeString(discount10);
        parcel.writeString(condition10);
        parcel.writeString(complimentaryService);
        parcel.writeString(conditionComplimentary);
        parcel.writeString(specialPackage);
        parcel.writeString(conditionSpecial);
    }

    public Shop(){

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private Shop(Parcel in){
        categoryId = in.readInt();
        shopName = in.readString();
        shopDescription = in.readString();
        shopWebsite = in.readString();
        shopNumber1 = in.readString();
        shopNumber2 = in.readString();
        shopPosition = in.readString();
        shopLogo = in.readString();
        discount10 = in.readString();
        condition10 = in.readString();
        complimentaryService = in.readString();
        conditionComplimentary = in.readString();
        specialPackage = in.readString();
        conditionSpecial = in.readString();
    }
}