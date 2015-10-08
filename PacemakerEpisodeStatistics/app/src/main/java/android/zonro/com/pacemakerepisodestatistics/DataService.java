package android.zonro.com.pacemakerepisodestatistics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataService extends Service {
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    private DataService ins = this;

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        DataService getService() {
            // Return this instance of LocalService so clients can call public methods
            return DataService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private SqlConnect sCon;
    public DataService()
    {
        Log.d("DataService", "con");
        new Thread(new Runnable() {
            @Override
            public void run() {
                sCon = SqlConnect.GetSqlConnect(getApplicationContext());
                sCon.createDatabase(getBaseContext());
                sCon.openDatabase();

                Intent intent = new Intent("0");
                LocalBroadcastManager.getInstance(ins).sendBroadcast(intent);
            }
        }).start();
    }

    public void update()
    {
        Intent intent = new Intent("0");
        LocalBroadcastManager.getInstance(ins).sendBroadcast(intent);
    }

    private List<PacemakerDataObject> curList = new ArrayList<PacemakerDataObject>();

    public List<PacemakerDataObject> getData()
    {
        sCon.updateData();
        curList = sCon.getCurList();
        return curList;
    }

}
