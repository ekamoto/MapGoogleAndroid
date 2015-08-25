package com.hisamoto.leandro.mapaandroid.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hisamoto.leandro.mapaandroid.R;
import com.hisamoto.leandro.mapaandroid.tracker.Rota;

import java.util.List;

/**
 * Created by leandro on 25/08/15.
 */
public class ListViewCustomizadoAdapter extends BaseAdapter {

    private List<Rota> listaRotas;
    private Context context;
    private static LayoutInflater inflater;
    private static View view;

    public ListViewCustomizadoAdapter(Context applicationContext, List<Rota> lista) {

        this.listaRotas = lista;
        this.context = applicationContext;
    }

    @Override
    public boolean hasStableIds() {
        return super.hasStableIds();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Item a ser apresentado na posição atual da ListView
        Rota item = listaRotas.get(position);

        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.activity_listview_customizado, parent, false);
        }

        // Imagem do item a ser apresentada na posição atual da ListView
        ImageView imagem = (ImageView) convertView.findViewById(R.id.imagem_activity);
        imagem.setImageResource(R.mipmap.ic_launcher);

        // Nome
        TextView nome = (TextView) convertView.findViewById(R.id.nome_activity);
        nome.setText(item.getOrigem());

        // E-mail
        TextView email = (TextView) convertView.findViewById(R.id.email_activity);
        email.setText(item.getDestino());

        // Hora
        TextView hora = (TextView) convertView.findViewById(R.id.hora_activity);
        hora.setText(item.getDescricao());

        // Status
        TextView status = (TextView) convertView.findViewById(R.id.status);

        // Zebrando a ListView
        if(position % 2 == 0) {

            convertView.setBackgroundColor(Color.WHITE);
            status.setText("Iniciado");
        } else {

            convertView.setBackgroundColor(Color.GRAY);
            status.setText("Finalizado");
        }

        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
    }

    @Override
    public int getCount() {

        return listaRotas.size();
    }

    @Override
    public Object getItem(int position) {

        return listaRotas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
