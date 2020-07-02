package it.flaviamagnoni.mpandroidchart_t;

import java.util.ArrayList;

public class BarInfo {
    String label;
    String value;

    public BarInfo(String label, String value){
        this.label = label;
        this.value = value;
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
