package android.zonro.com.e15.grp15.pacemakerepisodestatistics;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by kingofdamasta on 05-10-2015.
 */
public class PictureFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.picture_fragment_layout,container,false);

        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        ImageView img2 = (ImageView) view.findViewById(R.id.imageView2);

        String ET = ((EpisodeStatisticDetails)getActivity()).episodeType;

        switch(ET){
            case "Aterial fibrillation":
                img.setImageResource(R.drawable.af1);
                img2.setImageResource(R.drawable.af2);
                break;
            case "Other":
                img.setImageResource(R.drawable.other);
                img2.setImageResource(R.drawable.other);
                break;
            case "Shock":
                img.setImageResource(R.drawable.shock1);
                img2.setImageResource(R.drawable.shock2);
                break;
            case "Power failure":
                img.setImageResource(R.drawable.pf1);
                img2.setImageResource(R.drawable.pf2);
                break;
            default:
                img.setImageResource(R.drawable.pacemaker);
                img2.setImageResource(R.drawable.pacemaker);
                break;
        }


        Button btn = (Button) view.findViewById(R.id.buttonFragPic);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                ((EpisodeStatisticDetails)getActivity()).RemovePictures();
            }
        });
        return view;
    }

}
