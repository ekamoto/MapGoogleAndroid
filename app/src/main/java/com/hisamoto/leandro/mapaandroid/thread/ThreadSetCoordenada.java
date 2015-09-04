package com.hisamoto.leandro.mapaandroid.thread;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hisamoto.leandro.mapaandroid.dao.CoordenadaDao;
import com.hisamoto.leandro.mapaandroid.tracker.Coordenada;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leandro on 28/08/15.
 */
public class ThreadSetCoordenada extends AsyncTask<Void, Bundle, Void> {

    private LatLng current;
    private LatLng prev;
    private Context context;
    private int idRota;

    public ThreadSetCoordenada(Context context, int idRota) {

        this.context = context;
        this.idRota = idRota;
        Log.i("HisamotoCoordenada", "Entrou aqui 1 ");
    }

    @Override
    protected Void doInBackground(Void... params) {

        Log.i("HisamotoCoordenada", "Entrou aqui 2 ");

        CoordenadaDao cd = new CoordenadaDao(context);

        List<Coordenada> lista = new ArrayList<Coordenada>();

        int flag = 0;
        boolean focus = true;

        lista = cd.getCoordenadas(idRota);

        for (int i = 0; i < lista.size(); i++) {

            Log.i("HisamotoCoordenada", "Coordenada: " + lista.get(i).getLatitude());

            current = new LatLng(Double.parseDouble(lista.get(i).getLatitude()), Double.parseDouble(lista.get(i).getLongitude()));

            // When the first update comes, we have no previous points,hence this
            if (flag == 0) {

                prev = current;
                flag = 1;
            }

            Bundle b = new Bundle();
            b.putString("acao", "focus");
            b.putString("latitude",lista.get(i).getLatitude());
            b.putString("longitude",lista.get(i).getLongitude());

            if(focus) {

                focus = false;
                publishProgress(b);
            }

            b.putString("acao", "rota");
            b.putString("latitude_prev", ""+prev.latitude);
            b.putString("longitude_prev", ""+prev.longitude);
            publishProgress(b);

            prev = current;
            current = null;

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Bundle... values) {
        super.onProgressUpdate(values);

        LatLng current2 = new LatLng(Double.parseDouble(values[0].get("latitude").toString()), Double.parseDouble(values[0].get("longitude").toString()));

        if(values[0].get("acao").toString().equals("focus")) {

            Log.i("HisamotoCoordenada","Focus");

            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(current2, 16);
            ThreadGPSAsyncTask.mMap.animateCamera(update);
        } else if(values[0].get("acao").toString().equals("rota")) {

            Log.i("HisamotoCoordenada","Rota: Lat: "+Double.parseDouble(values[0].get("latitude_prev").toString())+" Long:"+Double.parseDouble(values[0].get("longitude_prev").toString()));

            LatLng prev2 = new LatLng(Double.parseDouble(values[0].get("latitude_prev").toString()), Double.parseDouble(values[0].get("longitude_prev").toString()));
            ThreadGPSAsyncTask.mMap.addPolyline((new PolylineOptions()).add(prev2, current2).width(6).color(Color.RED).visible(true));
        }
    }
}
