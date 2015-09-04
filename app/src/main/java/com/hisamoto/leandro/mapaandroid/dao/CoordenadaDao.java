package com.hisamoto.leandro.mapaandroid.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hisamoto.leandro.mapaandroid.db.DBHelper;
import com.hisamoto.leandro.mapaandroid.tracker.Coordenada;
import com.hisamoto.leandro.mapaandroid.tracker.Rota;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leandro on 27/08/15.
 */
public class CoordenadaDao {
    private DBHelper dbHelper;

    public CoordenadaDao(Context context) {

        this.dbHelper = new DBHelper(context);
    }

    public boolean cadastrarCoordenada(ContentValues values) {

        // Validação de campos e regra de negócio
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        long id = -1;

        try {

            id = db.insert("rota_coordenada", null, values);
            db.setTransactionSuccessful();
        } catch (android.database.SQLException e) {

            e.printStackTrace();
        } finally {

            db.endTransaction();
        }

        db.close();

        return id != -1 ? true : false;
    }

    public List<Coordenada> getCoordenadas(int idRota) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.beginTransaction();

        List<Coordenada> lista = new ArrayList<>();

        try {

            final Cursor c = db.query("rota_coordenada", new String[]{"latitude, longitude, tempo, id_rota"}, " id_rota=" + idRota + " ", null, null, null, null);

            if (c != null) {

                c.moveToFirst();

                while (c.isAfterLast() == false) {

                    Log.i("Hisamotoaasfdsa", "" + c.getString(2));
                    lista.add(new Coordenada(c.getString(2), c.getInt(3), c.getString(1), c.getString(0)));
                    c.moveToNext();
                }
            }

            db.setTransactionSuccessful();
        } catch (android.database.SQLException e) {

            e.printStackTrace();
        } finally {

            db.endTransaction();
        }

        db.close();
        return lista;
    }
}
