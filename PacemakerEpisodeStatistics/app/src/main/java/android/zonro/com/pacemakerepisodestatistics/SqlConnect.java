package android.zonro.com.pacemakerepisodestatistics;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Frank on 01-10-2015.
 */
public class SqlConnect extends SQLiteOpenHelper {
    private static SqlConnect instans = null;

    private static String dbName = "ITSMAP.sqlite";
    private static String path = "";
    private static SQLiteDatabase sdb;

    private ArrayList<PacemakerDataObject> curList;


    private SqlConnect(Context v)
    {
        super(v, dbName, null, 1);
        path = "/data/data/" + v.getApplicationContext().getPackageName() + "/databases";
        curList = new ArrayList<PacemakerDataObject>();
        Log.d("SqlConnect", path);
    }

    public static SqlConnect GetSqlConnect(Context v)
    {
        Log.d("SqlConnect", "getSqlConnect");
        if(instans == null)
        {
            instans = new SqlConnect(v);
        }
        return instans;
    }

    public void openDatabase()
    {
        Log.d("openDatabase", "trying to open databse");
        try
        {
            sdb = SQLiteDatabase.openDatabase(path + "/" + dbName, null,
                    SQLiteDatabase.OPEN_READWRITE);
            Log.d("openDatabase", "opened");
        } catch (Exception e)
        {
            Log.d("exception", "openDatabase");
            e.printStackTrace();
        }
    }

    public void updateData()
    {
        PacemakerDataObject curObj;


        try {
            Cursor c1 = sdb.rawQuery("SELECT * FROM PacemakerData", null);
            curList.clear();

            while (c1.moveToNext()) {
                curObj = new PacemakerDataObject();
                Log.d("UpdateData", c1.getString(1) + " " + c1.getString(2) + " " + c1.getString(3));
                curObj.setEpisodeType(c1.getString(1));
                curObj.setTransmissionsId(Integer.parseInt(c1.getString(2)));
                curObj.setDate(c1.getString(3));

                curList.add(curObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PacemakerDataObject> getCurList()
    {
        return curList;
    }

    public void createDatabase(Context v)
    {
        this.getReadableDatabase();
        try
        {
            InputStream myInput = v.getAssets().open(dbName);
            // Path to the just created empty db
            String outFileName = path +"/"+ dbName;
            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
