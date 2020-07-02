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
        return barInfos;
    }

}
