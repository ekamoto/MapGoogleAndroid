package com.hisamoto.leandro.mapaandroid.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hisamoto.leandro.mapaandroid.tracker.Rota;
import com.hisamoto.leandro.mapaandroid.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leandro on 24/08/15.
 */
public class RotaDao {

    private DBHelper dbHelper;

    public RotaDao(Context context) {

        this.dbHelper = new DBHelper(context);
    }

    public boolean cadastrarRota(ContentValues values) {

        // Validação de campos e regra de negócio
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        long id = -1;

        try {

            id = db.insert("rota",  null, values);
            db.setTransactionSuccessful();
        } catch (android.database.SQLException e) {

            e.printStackTrace();
        } finally {

            db.endTransaction();
        }

        db.close();

        return id!=-1?true:false;
    }

    public List<Rota> getUsuarios() {

        // Validação de campos e regra de negócio
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.beginTransaction();

        List<Rota> lista = new ArrayList<>();

        try {

            final Cursor c = db.query("rota", new String[]{"origem, destino, descricao"}, null, null, null, null, null);

            if(c!=null) {

                c.moveToFirst();

                while(c.isAfterLast() == false) {

                    Log.i("Hisamotoaasfdsa",""+c.getString(2));
                    lista.add(new Rota(c.getString(0), c.getString(1), c.getString(2)));
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
