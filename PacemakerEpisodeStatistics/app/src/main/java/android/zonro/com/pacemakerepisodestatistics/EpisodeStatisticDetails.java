package android.zonro.com.pacemakerepisodestatistics;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class EpisodeStatisticDetails extends AppCompatActivity {

    public TextView TVepisodeType;
    public TextView TVamount;
    public TextView TVpercentage;

    public TextView TVPMCounter;
    private static final String KEY_VALUE = "text";

    public int PMCounter = 0;

    PictureFragment frag;
    FragmentManager manager;

    public float cjan, cfeb, cmar, capr, cmay, cjun, cjul, caug, csep, coct, cnov, cdec;
    int newestyear;

    public String episodeType;
    public int amount;
    public int percentage;

    ArrayList<String> PacemakerDates = new ArrayList<String>();
    PacemakerDataObject PO;

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

        frag = new PictureFragment();
        manager  = getFragmentManager();

        TVepisodeType = (TextView)findViewById(R.id.textViewEpisodeTypeSet);
        TVamount = (TextView)findViewById(R.id.textViewAmountSet);
        TVpercentage = (TextView)findViewById(R.id.textViewPercentSet);

        TVPMCounter = (TextView)findViewById(R.id.textView3);
        if(savedInstanceState!=null){
            int savedCounter = savedInstanceState.getInt(KEY_VALUE);
            PMCounter = savedCounter;
        }

        TVPMCounter.setText(String.valueOf(PMCounter));

        Intent intent = getIntent();
        episodeType = intent.getStringExtra("episodeType");
        amount = intent.getIntExtra("transmissions", 0);
        percentage = intent.getIntExtra("procentTransmission", 0);
        PacemakerDates = intent.getStringArrayListExtra("dates");
        
        TVepisodeType.setText(episodeType);
        TVamount.setText(String.valueOf(amount));
        TVpercentage.setText(String.valueOf(percentage));

        graphSetup();

        Intent ServiceIntent=new Intent(this,TimerIntentService.class);
        startService(ServiceIntent);
    }

    public void SeeGraph(View v)
    {
        setContentView(chart);
        showingGraph = 1;
    }

    public void graphSetup(){
        for(int h=0; h<PacemakerDates.size(); h++)
        {
            //upToNCharacters from stackoverflow --- http://stackoverflow.com/questions/1583940/up-to-first-n-characters
            String year = PacemakerDates.get(h).substring(0, Math.min(PacemakerDates.get(h).length(), 4));

            if(Integer.parseInt(year) > newestyear)
                newestyear = Integer.parseInt(year);
        }

        for(int i = 0; i< PacemakerDates.size(); i++)
        {
            String year = PacemakerDates.get(i).substring(0, Math.min(PacemakerDates.get(i).length(), 4));

            if(Integer.parseInt(year) == newestyear)
            {

                char first = PacemakerDates.get(i).charAt(4);
                char second = PacemakerDates.get(i).charAt(5);

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



    private BroadcastReceiver MyBR = new BroadcastReceiver() {

        @Override

        public void onReceive(Context context, Intent intent) {
            PMCounter++;
            TVPMCounter.setText(String.valueOf(PMCounter));
        }
    };


    public void StartPictures(View v)
    {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.statistic_details_layout, frag,"picfrag");
        transaction.commit();
    }

    public void RemovePictures()
    {
        if(frag != null)
            manager.beginTransaction().remove(frag).commit();
    }

    @Override
    public void onBackPressed() {
        if(showingGraph == 1) {
            setContentView(R.layout.activity_episode_statistic_details);

            TVepisodeType = (TextView)findViewById(R.id.textViewEpisodeTypeSet);
            TVamount = (TextView)findViewById(R.id.textViewAmountSet);
            TVpercentage = (TextView)findViewById(R.id.textViewPercentSet);
            TVPMCounter = (TextView) findViewById(R.id.textView3);

            TVepisodeType.setText(episodeType);
            TVamount.setText(String.valueOf(amount));
            TVpercentage.setText(String.valueOf(percentage));
            TVPMCounter.setText(String.valueOf(PMCounter));
            showingGraph = 0;
        }

        else
            super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt(KEY_VALUE,PMCounter);

        RemovePictures();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(MyBR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(MyBR, new IntentFilter("secondPassed"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_episode_statistic_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
