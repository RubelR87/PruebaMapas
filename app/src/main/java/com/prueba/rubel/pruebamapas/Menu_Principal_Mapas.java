package com.prueba.rubel.pruebamapas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu_Principal_Mapas extends AppCompatActivity implements View.OnClickListener {
    Button btmapbasico,btmapmarkerubu, btmaptrazarruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__principal__mapas);

        btmaptrazarruta  = (Button) findViewById(R.id.btmaptrazarruta);
        btmapbasico = (Button) findViewById(R.id. btmapbasico);
        btmapmarkerubu = (Button) findViewById(R.id.btmapmarkerubu);

        btmapbasico.setOnClickListener(this);
        btmapmarkerubu.setOnClickListener(this);
        btmaptrazarruta.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btmaptrazarruta:
                startActivity(new Intent(this, Calcular_Distancia.class));

                break;
            case R.id.btmapbasico:

                startActivity(new Intent(this, MainActivity.class));

                break;

            case R.id.btmapmarkerubu:
                startActivity(new Intent(this, Mapa_GetLatitut_Longitud.class));


                break;

        }
    }
}
