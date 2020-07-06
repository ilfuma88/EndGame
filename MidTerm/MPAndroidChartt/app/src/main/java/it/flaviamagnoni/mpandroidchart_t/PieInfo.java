package it.flaviamagnoni.mpandroidchart_t;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.mikephil.charting.data.PieEntry;

public class PieInfo implements Parcelable{

    String label;
    float value;

    public PieInfo(String lab, float val){
        this.label = lab;
        this.value = val;
    }

    protected PieInfo(Parcel in) {
        label = in.readString();
        value = in.readFloat();
    }

    public static final Creator<PieInfo> CREATOR = new Creator<PieInfo>() {
        @Override
        public PieInfo createFromParcel(Parcel in) {
            return new PieInfo(in);
        }

        @Override
        public PieInfo[] newArray(int size) {
            return new PieInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.label);
        dest.writeFloat(this.value);
    }

    public String getLabel(){return label;}
    public float getValue(){return value;}

    public PieEntry toPieEntry(){
        return new PieEntry(value, label);
    }
}
