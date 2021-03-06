package android.zonro.com.e15.grp15.pacemakerepisodestatistics;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;


public class TimerIntentService extends IntentService {

    public TimerIntentService() {
        super("WorkerThread");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        while(true) {

            try {
                Thread.sleep(45000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent i = new Intent("secondPassed");
            LocalBroadcastManager.getInstance(this).sendBroadcast(i);

        }

    }
}
