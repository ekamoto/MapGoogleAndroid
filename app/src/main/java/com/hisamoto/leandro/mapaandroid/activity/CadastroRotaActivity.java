package com.hisamoto.leandro.mapaandroid.activity;

import android.app.Activity;
import android.content.ContentValues;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hisamoto.leandro.mapaandroid.R;
import com.hisamoto.leandro.mapaandroid.dao.RotaDao;

public class CadastroRotaActivity extends Activity {

    private EditText origem;
    private EditText destino;
    private EditText descricao;
    private Button cadastrar;
    private RotaDao rotaDao;
    private Button cancelar;
    private ImageButton voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_rota);

        rotaDao = new RotaDao(getApplicationContext());

        getComponentes();
        anexarEvent();
    }

    private void anexarEvent() {

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues values = new ContentValues();
                values.put("origem", origem.getText().toString());
                values.put("destino", destino.getText().toString());
                values.put("descricao", descricao.getText().toString());

                if (rotaDao.cadastrarRota(values)) {

                    Toast.makeText(getApplicationContext(), "Rota cadastrada com sucesso", Toast.LENGTH_SHORT).show();
                    origem.setText("");
                    destino.setText("");
                    descricao.setText("");
                } else {

                    Toast.makeText(getApplicationContext(), "Falha ao cadastrar rota", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getComponentes() {

        origem = (EditText) findViewById(R.id.origem);
        destino = (EditText) findViewById(R.id.destino);
        cadastrar = (Button) findViewById(R.id.cadastrar_rota);
        descricao = (EditText) findViewById(R.id.descricao);
        cancelar = (Button) findViewById(R.id.cancelar);
        voltar = (ImageButton) findViewById(R.id.img_voltar);
    }

}
