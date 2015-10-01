package android.zonro.com.pacemakerepisodestatistics;

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
}
