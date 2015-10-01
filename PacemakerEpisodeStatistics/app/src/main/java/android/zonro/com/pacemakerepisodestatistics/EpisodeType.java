package android.zonro.com.pacemakerepisodestatistics;

/**
 * Created by Frank on 01-10-2015.
 */
public class EpisodeType {
    private String name_;
    private int transmissions_;
    private int procentTransmission_;

    public EpisodeType(){}

    public String getName()
    {
        return name_;
    }

    public void setName(String name)
    {
        name_ = name;
    }

    public int getTransmissions()
    {
        return transmissions_;
    }

    public void setTransmissions(int transmissions)
    {
        transmissions_ = transmissions;
    }

    public int getProcentTransmission_()
    {
        return procentTransmission_;
    }

    public void setProcentTransmission(int procentTransmission)
    {
        procentTransmission_ = procentTransmission;
    }
}

