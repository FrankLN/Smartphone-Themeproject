package android.zonro.com.pacemakerepisodestatistics;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class EpisodeStatisticDetails extends AppCompatActivity {

    public TextView TVepisodeType;
    public TextView TVamount;
    public TextView TVpercentage;

    public String dataString;

    int showingGraph = 0;

    //BarChart made following this guide: http://code.tutsplus.com/tutorials/add-charts-to-your-android-app-using-mpandroidchart--cms-23335
    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();
    BarDataSet dataset = new BarDataSet(entries, "# of transmissions");
    BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_statistic_details);

        TVepisodeType = (TextView)findViewById(R.id.textViewEpisodeTypeSet);
        TVamount = (TextView)findViewById(R.id.textViewAmountSet);
        TVpercentage = (TextView)findViewById(R.id.textViewPercentSet);

        dataString = "50";
        Float fxyo= Float.parseFloat(dataString);

        entries.add(new BarEntry(fxyo, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));
        entries.add(new BarEntry(4f, 6));
        entries.add(new BarEntry(8f, 7));
        entries.add(new BarEntry(6f, 8));
        entries.add(new BarEntry(12f, 9));
        entries.add(new BarEntry(70f, 10));
        entries.add(new BarEntry(9f, 11));

        labels.add(getResources().getString(R.string.jan));
        labels.add(getResources().getString(R.string.feb));
        labels.add(getResources().getString(R.string.mar));
        labels.add(getResources().getString(R.string.apr));
        labels.add(getResources().getString(R.string.may));
        labels.add(getResources().getString(R.string.jun));
        labels.add(getResources().getString(R.string.jul));
        labels.add(getResources().getString(R.string.aug));
        labels.add(getResources().getString(R.string.sep));
        labels.add(getResources().getString(R.string.oct));
        labels.add(getResources().getString(R.string.nov));
        labels.add(getResources().getString(R.string.dec));

        BarData data = new BarData(labels, dataset);

        chart = new BarChart(getBaseContext());
        chart.setData(data);
        chart.setDescription("");
    }

    public void SeeGraph(View v)
    {
        setContentView(chart);
        showingGraph = 1;
    }

    @Override
    public void onBackPressed() {
        if(showingGraph == 1) {
            setContentView(R.layout.activity_episode_statistic_details);
            showingGraph = 0;
        }

        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_episode_statistic_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
