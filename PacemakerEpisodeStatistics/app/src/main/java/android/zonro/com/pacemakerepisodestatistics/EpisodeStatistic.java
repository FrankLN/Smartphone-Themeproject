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
        setContentView(R.layout.activity_episode_statistic);



        lv1 = (ListView)findViewById(R.id.listViewOne);
        lv2 = (ListView)findViewById(R.id.listViewTwo);
        lv3 = (ListView)findViewById(R.id.listViewThree);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                List<PacemakerDataObject> list = mService.getData();
                List<String> ep = new ArrayList<String>();
                for(int i = 0; i < list.size(); i++)
                {
                    if(!ep.contains(list.get(i).getEpisodeType())) {
                        ep.add(list.get(i).getEpisodeType());
                    }
                }

                ArrayAdapter<String> obj = new ArrayAdapter<String>(ins, android.R.layout.simple_list_item_1, ep);

                lv1.setAdapter(obj);
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter("0"));

        // Bind to DataService
        Intent intent = new Intent(this, DataService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("test", "" + view.getTransitionName());
            }
        });



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
