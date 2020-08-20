package it.flaviamagnoni.mpandroidchart_t.activities;

public class RadarChartRow {
    private String mLabel;
    private int mValue;

    public RadarChartRow(String label, int value){
        mLabel = label;
        mValue = value;
    }
    public void setLabel(String text){
        mLabel = text;
    }
    public void setValue(int value){
        mValue = value;
    }
    public String getLabel(){
        return mLabel;
    }
    public int getValue(){
        return mValue;
    }
}
