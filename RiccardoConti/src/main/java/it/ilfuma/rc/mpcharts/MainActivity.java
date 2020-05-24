package it.ilfuma.rc.mpcharts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    LineChart lcFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lcFirst = (LineChart) findViewById(R.id.lcFirst);

        //e' un ogetto che rende gli oggetti entry adatti ai linechart, il costruttore prende un array di entry
        LineDataSet ldsFirst = new LineDataSet(dataValue1(), "First Dataset");
        LineDataSet ldsSecond = new LineDataSet(dataValue2(), "Second  Dataset");

        //array di linee per metterne varie nel grafico
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(ldsFirst);
        dataSets.add(ldsSecond);
        LineData data = new LineData(dataSets);
        /**
        poteva esse usato quando volevo mettere una sola linea nel diagramma
        LineData data = new LineData(ldsFirst);
        */

        //lcFirst.setBackgroundColor(Color.GREEN);

        // sets the text that's shown if there's no data  in the chart
        lcFirst.setNoDataText("No Data");
        lcFirst.setNoDataTextColor(Color.BLUE);

        /**
         * manipulating background and chart borders
         */
        lcFirst.setDrawGridBackground(true); //sto metodo non sembrerebbe fare assolutamnet un cazzo
        lcFirst.setDrawBorders(true);
        lcFirst.setBorderWidth(5);  //vuole un float, che lui poi trattera' come dp
        lcFirst.setBorderColor(Color.CYAN);

        /**
         * chart description manipulation
         */
        Description description = new Description();
        description.setText("zoo  damn indian");
        description.setTextColor(Color.MAGENTA);
        description.setTextSize(20); //prende un float che poi trattera' come un valore in dp
        lcFirst.setDescription(description);

        /**
         * manipulating chart legend, The legend object is NOT available before setting data to the chart.
         */
        Legend legend = lcFirst.getLegend(); //retrive the legend object linked to the chart
        legend.setEnabled(true); //sets if the legend hs to be drawn or not
        legend.setTextColor(Color.LTGRAY);
        legend.setTextSize(15);
        legend.setForm(Legend.LegendForm.LINE); //sets the format of the "images" close to the legend texts
        legend.setFormSize(20);        //sets the size of the litte "images" of the legend (for line format it's its width
        legend.setXEntrySpace(15);     // sets the space between the entries of the legend
        legend.setFormToTextSpace(4);        //sets the space between a voice and its relative "image" in the legend

        /**making a custom legend for the chart, this has no linking with the data on the chart
         * note that to set a color for the form you cant use the colors stored in res.colors,
         * here we are using java color class
         */
        int colorArray[]= new int[] {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.MAGENTA};
        String legendName[] = {"Cow", "Dog", "Cat", "Rat"};

        LegendEntry legendEntries[] = new LegendEntry[4];
        for (int i =0; i<legendEntries.length; i++){
            LegendEntry entry = new LegendEntry();
            entry.formColor = colorArray[i];
            entry.label = String.valueOf(legendName[i]);
            legendEntries[i] = entry;
        }
        legend.setCustom(legendEntries);

        lcFirst.setData(data);
        lcFirst.invalidate();

    }


    private ArrayList<Entry> dataValue1(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 20));
        dataVals.add(new Entry(1, 24));
        dataVals.add(new Entry(2, 2));
        dataVals.add(new Entry(3, 10));
        dataVals.add(new Entry(4, 28));
        dataVals.add(new Entry(5, 4));
        dataVals.add(new Entry(6, 200));
        return dataVals;
    }

    /** se si usano linedataset con entry non ordinate crescenti sulle x, l'app crasha
    private ArrayList<Entry> dataValue2(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 50));
        dataVals.add(new Entry(1, 24));
        dataVals.add(new Entry(2, 2));
        dataVals.add(new Entry(40, 10));
        dataVals.add(new Entry(4, 28));
        dataVals.add(new Entry(57, 4));
        dataVals.add(new Entry(15, 100));
        return dataVals;
    }
    */

    private ArrayList<Entry> dataValue2(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 50));
        dataVals.add(new Entry(1, 24));
        dataVals.add(new Entry(2, 2));
        dataVals.add(new Entry(3, 10));
        dataVals.add(new Entry(4, 28));
        dataVals.add(new Entry(5, 4));
        dataVals.add(new Entry(6, 100));
        return dataVals;
    }


}
