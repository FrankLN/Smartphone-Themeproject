package android.zonro.com.pacemakerepisodestatistics;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EpisodeStatistic extends AppCompatActivity {
    DataService mService;
    boolean mBound = false;
    BroadcastReceiver receiver;
    private EpisodeStatistic ins = this;

    ListView lv1;
    ListView lv2;
    ListView lv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("EpisodeStatistic", "" + getResources().getConfiguration().orientation);
        if (getResources().getConfiguration().orientation == 1)
            setContentView(R.layout.activity_episode_statistic);
        else if (getResources().getConfiguration().orientation == 2)
            setContentView(R.layout.activity_episode_statistic_land);
        else
            setContentView(R.layout.activity_episode_statistic_port);


        lv1 = (ListView) findViewById(R.id.listViewOne);
        lv2 = (ListView) findViewById(R.id.listViewTwo);
        lv3 = (ListView) findViewById(R.id.listViewThree);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                List<PacemakerDataObject> list = mService.getData();
                List<String> ep = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    if (!ep.contains(list.get(i).getEpisodeType())) {
                        ep.add(list.get(i).getEpisodeType());
                    }
                }

                List<Integer> transmissions = new ArrayList<Integer>();
                for (int i = 0; i < ep.size(); i++) {
                    int count = 0;
                    List<Integer> ids = new ArrayList<Integer>();
                    for (int j = 0; j < list.size(); j++) {
                        if (ep.get(i).equals(list.get(j).getEpisodeType()) && !ids.contains(list.get(j).getTransmissionsId())) {
                            ids.add(list.get(j).getTransmissionsId());
                            count++;
                        }
                    }
                    Log.d("EpisodeStatistic", ep.get(i).toString() + ": " + count);
                    transmissions.add(count);
                }

                List<Integer> procentTransmissions = new ArrayList<Integer>();
                for (int i = 0; i < ep.size(); i++) {
                    int count = 0;
                    List<Integer> ids = new ArrayList<Integer>();
                    for (int j = 0; j < list.size(); j++) {
                        if (!ids.contains(list.get(j).getTransmissionsId())) {
                            ids.add(list.get(j).getTransmissionsId());
                            count++;
                        }
                    }

                    int percent = Math.round((float) (transmissions.get(i) * 100) / count);
                    Log.d("EpisodeStatistic", ep.get(i).toString() + ": " + percent);
                    procentTransmissions.add(percent);
                }

                ArrayAdapter<String> episodeTypeList = new ArrayAdapter<String>(ins, android.R.layout.simple_list_item_1, ep);
                ArrayAdapter<Integer> transmissionList = new ArrayAdapter<Integer>(ins, android.R.layout.simple_list_item_1, transmissions);
                ArrayAdapter<Integer> procentTransmissionList = new ArrayAdapter<Integer>(ins, android.R.layout.simple_list_item_1, procentTransmissions);

                if (lv1 != null) {
                    lv1.setAdapter(episodeTypeList);
                }

                if (lv2 != null)
                    lv2.setAdapter(transmissionList);

                if (lv3 != null)
                    lv3.setAdapter(procentTransmissionList);

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter("0"));

        // Bind to DataService
        Intent intent = new Intent(this, DataService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        if (lv1 != null) {
            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("test", "" + ((TextView) view).getText().toString());

                    Intent intent = new Intent(ins, EpisodeStatisticDetails.class);
                    intent.putExtra("episodeType", ((TextView) view).getText().toString());
                    intent.putExtra("transmissions", 2);
                    intent.putExtra("procentTransmission", 5);

                    startActivity(intent);
                }
            });
        }
    }

    protected void onResume(){
        super.onResume();
        Log.d("EpisodeStatistic", "" + getResources().getConfiguration().orientation);
        if(getResources().getConfiguration().orientation == 1)
            setContentView(R.layout.activity_episode_statistic_port);
        else if(getResources().getConfiguration().orientation == 2)
            setContentView(R.layout.activity_episode_statistic_land);
        else
            setContentView(R.layout.activity_episode_statistic);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }


    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DataService.LocalBinder binder = (DataService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

}
