package com.hisamoto.leandro.mapaandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.hisamoto.leandro.mapaandroid.R;
import com.hisamoto.leandro.mapaandroid.thread.ThreadSetCoordenada;
import com.hisamoto.leandro.mapaandroid.thread.ThreadGPSAsyncTask;
import com.hisamoto.leandro.mapaandroid.tracker.Coordenada;

public class ShowRotaActivity extends Activity {

    private TextView campoOrigemDestino;
    private TextView campoDescricao;
    private TextView status;
    private Button iniciarRota;
    private Button plotar;
    private ImageButton voltar;
    private int idRota;
    private Button pausarRota;

    private LatLng current;
    private LatLng prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_rota);

        campoOrigemDestino = (TextView) findViewById(R.id.show_origem_destino);
        status = (TextView) findViewById(R.id.status);
        campoDescricao = (TextView) findViewById(R.id.show_descricao);
        iniciarRota = (Button) findViewById(R.id.iniciar_rota);
        plotar = (Button) findViewById(R.id.plotar_pontos);
        pausarRota = (Button) findViewById(R.id.pausar_rota);

        Intent i = getIntent();
        String origem = i.getExtras().getString("origem");
        String destino = i.getExtras().getString("destino");
        String descricao = i.getExtras().getString("descricao");
        idRota = Integer.parseInt(i.getExtras().getString("idRota"));

        campoOrigemDestino.setText(origem + " / " + destino);
        campoDescricao.setText(descricao);

        status.setText(ThreadGPSAsyncTask.salveCoordenadas ? "[Iniciado]" : "[Pausado]");

        Log.i("HisamotoTeste", "Activity recebimento: " + origem);

        iniciarRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ThreadGPSAsyncTask.salveCoordenadas = true;
                ThreadGPSAsyncTask.coordenada = new Coordenada();
                ThreadGPSAsyncTask.coordenada.setIdRota(idRota);

                Toast.makeText(getApplicationContext(), "Iniciando gravação de rota", Toast.LENGTH_SHORT).show();
                status.setText("[Iniciado]");
            }
        });

        pausarRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ThreadGPSAsyncTask.salveCoordenadas = false;
                ThreadGPSAsyncTask.coordenada.setIdRota(0);

                Toast.makeText(getApplicationContext(), "Parando gravação de rota", Toast.LENGTH_SHORT).show();

                status.setText("[Pausado]");
            }
        });

        plotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Parando rastreio", Toast.LENGTH_SHORT).show();
                ThreadGPSAsyncTask.isProcess = false;
                Toast.makeText(getApplicationContext(), "Plotando pontos", Toast.LENGTH_SHORT).show();

                ThreadSetCoordenada setCoordenada = new ThreadSetCoordenada(getApplicationContext(), idRota);
                setCoordenada.execute();

                Intent i = new Intent();
                i.setClass(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        voltar = (ImageButton) findViewById(R.id.img_voltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_rota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
