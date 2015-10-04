package android.zonro.com.pacemakerepisodestatistics;

import java.util.ArrayList;

/**
 * Created by Frank on 01-10-2015.
 */
public class PacemakerDataObject {
    private String episodeType_;
    private int transmissionsId_;
    private String date_;

    public PacemakerDataObject()
    {
    }
    public PacemakerDataObject(String episodeType, int transmissionsId, String date)
    {
        episodeType_ = episodeType;
        transmissionsId_ = transmissionsId;
        date_ = date;
    }

    public String getEpisodeType()
    {
        return episodeType_;
    }

    public void setEpisodeType(String episodeType)
    {
        episodeType_ = episodeType;
    }

    public int getTransmissionsId()
    {
        return transmissionsId_;
    }

    public void setTransmissionsId(int transmissionsId)
    {
        transmissionsId_ = transmissionsId;
    }

    public String getDate()
    {
        return date_;
    }

    public void setDate(String date)
    {
        date_ = date;
    }

    public ArrayList<PacemakerDataObject> getList()
    {
        ArrayList<PacemakerDataObject> result = new ArrayList<PacemakerDataObject>();

        result.add(new PacemakerDataObject("Aterial Fibrillation", 1, "20151220113452"));
        result.add(new PacemakerDataObject("Shock", 1, "20150926103452"));
        result.add(new PacemakerDataObject("Other", 2, "20150927213452"));

        return result;
    }
}
