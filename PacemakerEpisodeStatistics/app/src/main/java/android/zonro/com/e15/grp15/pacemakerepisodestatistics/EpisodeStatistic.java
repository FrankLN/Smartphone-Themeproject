package android.zonro.com.e15.grp15.pacemakerepisodestatistics;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    List<PacemakerDataObject> list;
    List<String> ep;
    List<Integer> transmissions;
    List<Integer> procentTransmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Determine screen orientation
        if (getResources().getConfiguration().orientation == 1)
            setContentView(R.layout.activity_episode_statistic_port);
        else if (getResources().getConfiguration().orientation == 2)
            setContentView(R.layout.activity_episode_statistic_land);
        else
            setContentView(R.layout.activity_episode_statistic_port);

        // Set ListViews
        lv1 = (ListView) findViewById(R.id.listViewOne);
        lv2 = (ListView) findViewById(R.id.listViewTwo);
        lv3 = (ListView) findViewById(R.id.listViewThree);

        // Set onReceive method
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                    broadcastReceived();
                }
        };



        // Bind to DataService
        Intent intent = new Intent(this, DataService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        // If not null set onClickListener on first ListView
        if (lv1 != null) {
            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("test", "" + ((TextView) view).getText().toString());

                    ArrayList<String> dates = new ArrayList<String>();
                    for(int i=0;i<list.size();i++)
                    {
                        if(ep.get(position).equals(list.get(i).getEpisodeType()))
                            dates.add(list.get(i).getDate());
                    }

                    Intent intent = new Intent(ins, EpisodeStatisticDetails.class);
                    intent.putExtra("episodeType", ep.get(position).toString());
                    intent.putExtra("transmissions", transmissions.get(position));
                    intent.putExtra("procentTransmission", procentTransmission.get(position));
                    intent.putStringArrayListExtra("dates", dates);

                    startActivity(intent);
                }
            });
        }
    }

    // Method called by onReceived broadcast
    private void broadcastReceived()
    {
        // Get all entries from db
        list = mService.getData();

        // Make a list of all episodeType
        ep = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            if (!ep.contains(list.get(i).getEpisodeType())) {
                ep.add(list.get(i).getEpisodeType());
            }
        }
        //...

        // Determine the number of unique transmission each episodeType is part of
        transmissions = new ArrayList<Integer>();
        for (int i = 0; i < ep.size(); i++) {
            int count = 0;
            List<Integer> ids = new ArrayList<Integer>();
            for (int j = 0; j < list.size(); j++) {
                if (ep.get(i).equals(list.get(j).getEpisodeType()) && !ids.contains(list.get(j).getTransmissionsId())) {
                    ids.add(list.get(j).getTransmissionsId());
                    count++;
                }
            }
            transmissions.add(count);
        }

        // Determine the procent of all transmission each episodeType is part of
        procentTransmission = new ArrayList<Integer>();
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
            procentTransmission.add(percent);
        }

        // Setup the ArrayAdapters for the ListViews
        ArrayAdapter<String> episodeTypeList = new ArrayAdapter<String>(ins, android.R.layout.simple_list_item_1, ep);
        ArrayAdapter<Integer> transmissionList = new ArrayAdapter<Integer>(ins, android.R.layout.simple_list_item_1, transmissions);
        ArrayAdapter<Integer> procentTransmissionList = new ArrayAdapter<Integer>(ins, android.R.layout.simple_list_item_1, procentTransmission);


        // Fill up the ListViews if they exist
        if (lv1 != null) {
            lv1.setAdapter(episodeTypeList);
        }

        if (lv2 != null) {
            lv2.setAdapter(transmissionList);
        }

        if (lv3 != null) {
            lv3.setAdapter(procentTransmissionList);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register broadcaster
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter("0"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!mBound) ;

                mService.update();
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }

        super.onDestroy();
    }

    // Code found on: http://developer.android.com/guide/components/bound-services.html
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
