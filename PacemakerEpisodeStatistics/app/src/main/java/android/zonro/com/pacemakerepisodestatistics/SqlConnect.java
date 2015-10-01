package android.zonro.com.pacemakerepisodestatistics;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Frank on 01-10-2015.
 */
public class SqlConnect extends SQLiteOpenHelper {
    private static SqlConnect instans = null;

    private static String dbName = "ITSMAP.sqlite";
    private static String path = "";
    private static SQLiteDatabase sdb;


    private SqlConnect(Context v)
    {
        super(v, dbName, null, 1);
        path = "/data/data/" + v.getApplicationContext().getPackageName() + "/databases";
        Log.d("SqlConnect", path);
    }

    public static SqlConnect GetSqlConnect(Context v)
    {
        if(instans == null)
        {
            instans = new SqlConnect(v);
        }
        return instans;
    }

    public boolean checkDatabase() {
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(path + "/" + dbName, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(db == null) {
            return false;
        }
        else {
            db.close();
            return true;
        }
    }

    public void openDatabase()
    {
        try
        {
            sdb = SQLiteDatabase.openDatabase(path + "/" + dbName, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public ArrayList<PacemakerDataObject> getData()
    {
        ArrayList<PacemakerDataObject> result = new ArrayList<PacemakerDataObject>();
        PacemakerDataObject curObj;
        try {
            Cursor c1 = sdb.rawQuery("SELECT * FROM PacemakerData", null);

            while (c1.moveToNext()) {
                curObj = new PacemakerDataObject();

                curObj.setEpisodeType(c1.getString(1));
                curObj.setTransmissionsId(Integer.parseInt(c1.getString(2)));
                curObj.setDate(c1.getString(3));

                result.add(curObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
