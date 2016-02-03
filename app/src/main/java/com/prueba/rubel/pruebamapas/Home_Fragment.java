package com.prueba.rubel.pruebamapas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by user on 02/02/2016.
 */
public class Home_Fragment extends Fragment implements View.OnClickListener {
    Button btmapbasico,btmapmarkerubu, btmaptrazarruta;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        // inflater.inflate(R.layout.home_fragment,null);



        btmaptrazarruta  = (Button) rootView.findViewById(R.id.btmaptrazarruta);
        btmapbasico = (Button) rootView.findViewById(R.id.btmapbasico);
        btmapmarkerubu = (Button) rootView.findViewById(R.id.btmapmarkerubu);

        btmapbasico.setOnClickListener(this);
        btmapmarkerubu.setOnClickListener(this);
        btmaptrazarruta.setOnClickListener(this);

        return rootView;


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btmaptrazarruta:
                startActivity(new Intent(getActivity(), Calcular_Distancia.class));
                break;


            case R.id.btmapbasico:


                startActivity(new Intent(getActivity(),  MainActivity.class));

                break;

            case R.id.btmapmarkerubu:

                startActivity(new Intent(getActivity(), Mapa_GetLatitut_Longitud.class));

                break;
        }
    }
}
