package com.hisamoto.leandro.mapaandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.hisamoto.leandro.mapaandroid.R;
import com.hisamoto.leandro.mapaandroid.thread.ThreadGPSAsyncTask;

public class MainActivity extends ActionBarActivity {

    private static GoogleMap mMap;
    public static boolean iniciado = false;
    public static ThreadGPSAsyncTask threadAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(!iniciado) {

            Toast.makeText(getApplicationContext(),"Loading...",Toast.LENGTH_SHORT).show();

            setUpMapIfNeeded();

            threadAsyncTask = new ThreadGPSAsyncTask(getApplicationContext(), mMap);

            Bundle bundle = new Bundle();
            bundle.putString("msg", "Passando parâmetro para o processamento");

            threadAsyncTask.execute();

            iniciado = true;
        } else {

            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            mMap.getUiSettings().setZoomGesturesEnabled(true);

            Toast.makeText(getApplicationContext(),"Atualizando mapa...",Toast.LENGTH_SHORT).show();
            threadAsyncTask.updateMap(mMap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Intent i = new Intent();
            i.setClass(getApplicationContext(), SettingsActivity.class);
            startActivity(i);

            return true;
        } else if(id == R.id.cadastro_rotas){

            Intent e = new Intent();
            e.setClass(getApplicationContext(), CadastroRotaActivity.class);
            startActivity(e);

        }else if(id == R.id.lista_rotas){

            Intent e = new Intent();
            e.setClass(getApplicationContext(), ListPesquisaActivity.class);
            startActivity(e);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated..
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {

        if (mMap == null) {

            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
        }
    }
}
