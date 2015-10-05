package android.zonro.com.pacemakerepisodestatistics;

import android.content.Context;
import android.content.Intent;
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

    public float cjan, cfeb, cmar, capr, cmay, cjun, cjul, caug, csep, coct, cnov, cdec;
    int newestyear;

    public String episodeType;
    public int amount;
    public int percentage;

    ArrayList<PacemakerDataObject> PacemakerObjects = new ArrayList<PacemakerDataObject>();
    PacemakerDataObject PO;

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

        Intent intent = getIntent();
        episodeType = intent.getStringExtra("episodeType");
        amount = intent.getIntExtra("transmissions", 0);
        percentage = intent.getIntExtra("procentTransmission", 0);

        TVepisodeType.setText(episodeType);
        TVamount.setText(String.valueOf(amount));
        TVpercentage.setText(String.valueOf(percentage));

        PacemakerObjects = PO.getList();

        for(int h=0; h<PacemakerObjects.size(); h++)
        {
            //upToNCharacters from stackoverflow --- http://stackoverflow.com/questions/1583940/up-to-first-n-characters
            String year = PacemakerObjects.get(h).getDate().substring(0, Math.min(PacemakerObjects.get(h).getDate().length(), 4));

            if(Integer.parseInt(year) > newestyear)
            newestyear = Integer.parseInt(year);
        }


        for(int i = 0; i< PacemakerObjects.size(); i++)
        {
            String year = PacemakerObjects.get(i).getDate().substring(0, Math.min(PacemakerObjects.get(i).getDate().length(), 4));

            if(Integer.parseInt(year) == newestyear && PacemakerObjects.get(i).getEpisodeType() == episodeType)
            {

                char first = PacemakerObjects.get(i).getDate().charAt(4);
                char second = PacemakerObjects.get(i).getDate().charAt(5);

                String temp = String.valueOf(first) + String.valueOf(second);

                switch (temp) {
                    case "01":
                        cjan++;
                        break;
                    case "02":
                        cfeb++;
                        break;
                    case "03":
                        cmar++;
                        break;
                    case "04":
                        capr++;
                        break;
                    case "05":
                        cmay++;
                        break;
                    case "06":
                        cjun++;
                        break;
                    case "07":
                        cjul++;
                        break;
                    case "08":
                        caug++;
                        break;
                    case "09":
                        csep++;
                        break;
                    case "10":
                        coct++;
                        break;
                    case "11":
                        cnov++;
                        break;
                    case "12":
                        cdec++;
                        break;
                }
            }
        }
        
        entries.add(new BarEntry(cjan, 0));
        entries.add(new BarEntry(cfeb, 1));
        entries.add(new BarEntry(cmar, 2));
        entries.add(new BarEntry(capr, 3));
        entries.add(new BarEntry(cmay, 4));
        entries.add(new BarEntry(cjun, 5));
        entries.add(new BarEntry(cjul, 6));
        entries.add(new BarEntry(caug, 7));
        entries.add(new BarEntry(csep, 8));
        entries.add(new BarEntry(coct, 9));
        entries.add(new BarEntry(cnov, 10));
        entries.add(new BarEntry(cdec, 11));

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
