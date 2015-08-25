package com.hisamoto.leandro.mapaandroid.thread;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hisamoto.leandro.mapaandroid.tracker.LogMap;
import com.hisamoto.leandro.mapaandroid.R;

/**
 * @author Leandro Shindi Ekamoto
 * @version 1.0 12/08/15.
 *          AsyncTask <Parâmetro doInBackground, Parâmetro para onProgressUpdate, Retorno doInBackground>
 */
public class ThreadAsyncTask extends AsyncTask<Bundle, Bundle, Void> {

    private Context context;
    private boolean process;
    private int cont;
    private static LocationManager lm;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    private boolean isProcess = true;
    private static GoogleMap mMap;
    private static double lat;
    private static double longi;
    private String meio;
    private int flag = 0;
    private LatLng current;
    private LatLng prev;
    private boolean focus = true;
    private SharedPreferences sharedPreferences;
    public static boolean showCoordinate;
    public static boolean saveFile;
    public static int timeLoop;
    private static LatLng PONTO;
    private static LatLng PONTO_INICIAL;

    public ThreadAsyncTask(Context context, GoogleMap m) {

        this.context = context;
        this.process = true;
        this.cont = 1;
        this.mMap = m;

        if (lm == null) {

            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        try {

            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {

            Log.i("GPSAndroid", "ERRO: GPS_PROVIDER");
        }

        try {

            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {

            Log.i("GPSAndroid", "ERRO: NETWORK_PROVIDER");
        }

        if (!gps_enabled && !network_enabled) {

            // return null;
        }

        if (gps_enabled) {

            Log.i("GPSAndroid", "UPDATE GPS......");
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        }

        if (network_enabled) {

            Log.i("GPSAndroid", "UPDATE NETWORK_PROVIDER......");
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
        }
    }

    public boolean isGps_enabled() {
        return gps_enabled;
    }

    public void setGps_enabled(boolean gps_enabled) {
        this.gps_enabled = gps_enabled;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isProcess() {
        return process;
    }

    public void setIsProcess(boolean isProcess) {
        this.isProcess = isProcess;
    }

    public static GoogleMap getmMap() {
        return mMap;
    }

    public static void setmMap(GoogleMap mMap) {
        ThreadAsyncTask.mMap = mMap;
    }

    public static double getLat() {
        return lat;
    }

    public static void setLat(double lat) {
        ThreadAsyncTask.lat = lat;
    }

    public static double getLongi() {
        return longi;
    }

    public static void setLongi(double longi) {
        ThreadAsyncTask.longi = longi;
    }

    public String getMeio() {
        return meio;
    }

    public void setMeio(String meio) {
        this.meio = meio;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public LatLng getCurrent() {
        return current;
    }

    public void setCurrent(LatLng current) {
        this.current = current;
    }

    public LatLng getPrev() {
        return prev;
    }

    public void setPrev(LatLng prev) {
        this.prev = prev;
    }

    public LocationListener getLocationListenerGps() {
        return locationListenerGps;
    }

    public void setLocationListenerGps(LocationListener locationListenerGps) {
        this.locationListenerGps = locationListenerGps;
    }

    public LocationListener getLocationListenerNetwork() {
        return locationListenerNetwork;
    }

    public void setLocationListenerNetwork(LocationListener locationListenerNetwork) {
        this.locationListenerNetwork = locationListenerNetwork;
    }

    public void setProcess(boolean process) {
        this.process = process;
    }

    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    public static LocationManager getLm() {
        return lm;
    }

    public static void setLm(LocationManager lm) {
        ThreadAsyncTask.lm = lm;
    }

    public boolean isNetwork_enabled() {
        return network_enabled;
    }

    public void setNetwork_enabled(boolean network_enabled) {
        this.network_enabled = network_enabled;
    }

    public void updateMap(GoogleMap m) {

        this.mMap = m;
        focus = true;

        if(PONTO_INICIAL != null)
            setarPonto(PONTO_INICIAL);
    }

    public Bundle getDados(Location location) {

        Bundle bundle = new Bundle();
        bundle.putString("latitude", "" + location.getLatitude());
        bundle.putString("longitude", "" + location.getLongitude());
        bundle.putString("time", "" + location.getTime());

        return bundle;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {

            publishProgress(getDados(location));
            meio = "GPS";
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {

        public void onLocationChanged(Location location) {

            publishProgress(getDados(location));
            meio = "Network";
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @Override
    protected Void doInBackground(Bundle... params) {

        while (isProcess) {

            Log.i("GPSAndroid", "Atualizando localização");

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            showCoordinate = sharedPreferences.getBoolean("show_coordinate", true);
            saveFile = sharedPreferences.getBoolean("save_file", true);
            timeLoop = Integer.parseInt(sharedPreferences.getString("time_loop", "5"));

            Location net_loc = null, gps_loc = null;

            gps_loc = null;
            net_loc = null;

            if (gps_enabled) {

                gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            if (network_enabled) {

                net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (gps_loc != null) {

                Log.i("GPSAndroid", "GPS......");
                publishProgress(getDados(gps_loc));
                meio = "GPS";
            } else if (net_loc != null) {

                Log.i("GPSAndroid", "NET......");
                publishProgress(getDados(net_loc));
                meio = "NET";
            }

            try {
                Thread.sleep(timeLoop*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public String getIMEI() {

        String IMEI = "";

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = telephonyManager.getDeviceId();

        return IMEI;
    }

    @Override
    protected void onProgressUpdate(Bundle... values) {

        lat = Double.parseDouble(values[0].get("latitude").toString());
        longi = Double.parseDouble(values[0].get("longitude").toString());

        PONTO = new LatLng(lat, longi);

        if(PONTO_INICIAL == null && PONTO != null) {

            PONTO_INICIAL = PONTO;
        }

        if(focus) {

            // Setar ponto
            setarPonto(PONTO);
        }

        // Desenhar Rota
        desenharRota(lat, longi);

        // Grava no arquivo log
        if(saveFile) {

            String text = "C;" + getIMEI() + ";" + values[0].get("latitude") + ";" + values[0].get("longitude") + ";" + values[0].get("time") + ";gps" + "\n";
            LogMap.getInstance().writeToLog(text);
        }

        // Mostrar coordenadas nas tela
        if(showCoordinate) {

            String texto = "[" + meio + "]" + lat + "/" + longi;
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show();
        }
    }

    public void setarPonto(LatLng PONTO) {

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_media_play);

        // Marcar ponto
        this.mMap.addMarker(new MarkerOptions().position(PONTO).title("Euuuu").icon(icon));

        if(focus) {

            // Zoom
            this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PONTO, 15));
            focus = false;
        }
    }

    public void desenharRota(double latitude, double longitude) {

        current = new LatLng(latitude, longitude);

        //when the first update comes, we have no previous points,hence this
        if (flag == 0) {

            prev = current;
            flag = 1;
        }

        if(focus) {

            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(current, 16);
            this.mMap.animateCamera(update);
            focus = false;
        }

        this.mMap.addPolyline((new PolylineOptions()).add(prev, current).width(6).color(Color.BLUE).visible(true));
        prev = current;
        current = null;
    }
}