package com.hisamoto.leandro.mapaandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by leandro on 24/08/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static String NAME_DB = "mapaGoogle";
    private static Integer VERSION_DB = 1;
    private String sql_rota = "CREATE TABLE rota( "+
            "_id INTEGER PRIMARY KEY AUTOINCREMENT "+
            ", origem TEXT "+
            ", destino TEXT  "+
            ", descricao TEXT "+
            ", dthora_fim LONG "+
            ", dthora_inicio LONG );";
    private String sql_rota_coordenada = "CREATE TABLE rota_coordenada( " +
            "id_rota INTEGER" +
            ", latidude INTEGER " +
            ", longitude INTEGER " +
            ", tempo LONG " +
            ", destino TEXT );";

    public DBHelper(Context context) {

        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sql_rota);
        db.execSQL(sql_rota_coordenada);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


