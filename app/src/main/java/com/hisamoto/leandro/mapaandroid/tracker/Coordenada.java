package com.hisamoto.leandro.mapaandroid.tracker;

/**
 * Created by leandro on 27/08/15.
 */
public class Coordenada {
    private String latitude;
    private String longitude;
    private Integer idRota;
    private String tempo;

    public Coordenada() {

    }

    public Coordenada(String tempo, Integer idRota, String longitude, String latitude) {
        this.tempo = tempo;
        this.idRota = idRota;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getIdRota() {
        return idRota;
    }

    public void setIdRota(Integer idRota) {
        this.idRota = idRota;
    }

    public String getTempo() {
        return this.tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }
}
