package com.example.arjunv.lpg;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFrag extends Fragment implements View.OnClickListener{


    public HomeFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);





         CardView bookCard,historyCard,servicesCard,ivrsCard;

         bookCard= view.findViewById(R.id.bookCard);
         historyCard=view.findViewById(R.id.historyCard);
         servicesCard=view.findViewById(R.id.servicesCard);
         ivrsCard=view.findViewById(R.id.ivrsCard);

         bookCard.setOnClickListener(this);
         historyCard.setOnClickListener(this);
         servicesCard.setOnClickListener(this);
         ivrsCard.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch(v.getId()){
            case (R.id.bookCard): i=new Intent(getActivity(), BookActivity.class);startActivity(i);break;
            case (R.id.historyCard): i=new Intent(getActivity(), HistoryActivity.class);startActivity(i);break;
            case (R.id.servicesCard): i=new Intent(getActivity(), ServicesActivity.class);startActivity(i);break;
            case (R.id.ivrsCard): i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:+917710927028"));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);break;

            default:break;

        }


    }
}
