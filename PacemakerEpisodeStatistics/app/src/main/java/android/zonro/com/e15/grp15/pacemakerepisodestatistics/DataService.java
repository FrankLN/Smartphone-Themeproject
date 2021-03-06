package android.zonro.com.e15.grp15.pacemakerepisodestatistics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

// Inspired from: http://developer.android.com/guide/components/bound-services.html
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
    private boolean connected = false;
    public DataService()
    {
        Log.d("DataService", "con");
        init(DataService.this);
    }

    public void init(final Context context)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sCon = SqlConnect.GetSqlConnect(context);
                File file = new File(SqlConnect.path +"/"+ SqlConnect.dbName);
                if(!file.exists())
                    sCon.createDatabase(getBaseContext());
                sCon.openDatabase();
                connected = true;
            }
        }).start();
    }

    public void update()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!connected) ;
                Intent intent = new Intent("0");
                LocalBroadcastManager.getInstance(ins).sendBroadcast(intent);
                getDbFile();
                LocalBroadcastManager.getInstance(ins).sendBroadcast(intent);
            }
        }).start();
    }

    private List<PacemakerDataObject> curList = new ArrayList<PacemakerDataObject>();

    public List<PacemakerDataObject> getData()
    {
        sCon.updateData();
        curList = sCon.getCurList();
        return curList;
    }

    // Inspired from: http://www.helloandroid.com/tutorials/how-download-fileimage-url-your-device
    public void getDbFile()
    {
        try{
            URL url = new URL("https://www.dropbox.com/s/h4lom5ara4fd9h3/ITSMAP.sqlite?dl=1");
            String outFileName = SqlConnect.path +"/"+ SqlConnect.dbName;

            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1)
            {
                Log.d("Download", "" + length);
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            is.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy()
    {
        connected = false;
        sCon.close();
    }

}
