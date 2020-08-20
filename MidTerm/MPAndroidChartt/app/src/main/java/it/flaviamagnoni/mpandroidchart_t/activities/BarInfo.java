package it.flaviamagnoni.mpandroidchart_t.activities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BarInfo implements Parcelable {
    String label;
    String value;

    public BarInfo(String label, String value){
        this.label = label;
        this.value = value;
    }

    public static final Creator<BarInfo> CREATOR = new Creator<BarInfo>() {
        @Override
        public BarInfo createFromParcel(Parcel in) {
            return new BarInfo(in);
        }

        @Override
        public BarInfo[] newArray(int size) {
            return new BarInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.label);
        dest.writeString(this.value);
    }

    public BarInfo(Parcel in){
        this.label = in.readString();
        this.value = in.readString();
    }

    public static ArrayList<BarInfo> SomeBarInfo(){
        ArrayList<BarInfo> barInfos = new ArrayList<>();
        barInfos.add(new BarInfo("monday","67"));
        barInfos.add(new BarInfo("tuesday","47"));
        barInfos.add(new BarInfo("wednesday","33"));
        barInfos.add(new BarInfo("thursday","44"));
        barInfos.add(new BarInfo("friday","20"));
        barInfos.add(new BarInfo("saturday","10"));
        barInfos.add(new BarInfo("sunday","10"));
        return barInfos;
    }
}
