package com.hisamoto.leandro.mapaandroid.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hisamoto.leandro.mapaandroid.R;
import com.hisamoto.leandro.mapaandroid.adapter.ListViewCustomizadoAdapter;
import com.hisamoto.leandro.mapaandroid.tracker.Rota;
import com.hisamoto.leandro.mapaandroid.dao.RotaDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leandro Shindi
 * @version 1.0 30/06/15.
 */
public class ListPesquisaActivity extends ListActivity {

    private EditText eText;
    private RotaDao rotaDao;
    private List<Rota> listaRota;
    private List<Rota> listaAux;
    private ImageButton img_voltar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lista_pesquisa_activity);

        rotaDao = new RotaDao(getApplicationContext());
        eText = (EditText) findViewById(R.id.campo_busca);
        img_voltar = (ImageButton) findViewById(R.id.img_voltar);

        listaRota = new ArrayList<Rota>();
        listaRota = rotaDao.getUsuarios();

        setListAdapter(new ListViewCustomizadoAdapter(getApplicationContext(), listaRota));

        eText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable arg0) {

                // TODO Auto-generated method stub
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                // TODO Auto-generated method stub
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                listaAux = new ArrayList<Rota>();
                for (int i = 0; i < listaRota.size(); i++) {

                    if (listaRota.get(i).getOrigem().contains(arg0)) {

                        listaAux.add(listaRota.get(i));
                    }
                }

                setListAdapter(new ListViewCustomizadoAdapter(getApplicationContext(), listaAux));
            }
        });

        img_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}