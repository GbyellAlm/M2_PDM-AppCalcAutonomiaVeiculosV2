package com.example.appcalcautonomiaveiculosv2.Adaptador;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcalcautonomiaveiculosv2.Modelo.DadosAbastecimento;
import com.example.appcalcautonomiaveiculosv2.Modelo.DadosAbastecimentoDao;
import com.example.appcalcautonomiaveiculosv2.R;

//import RecyclerView.Adapter;

// ESSE CARA É O CARA Q VAI ADM A CRIAÇÃO DE GAVETAS E A COLOCAÇÃO DE ITENS NAS GAVETAS!
public class DadosAbastecimentoAdaptador extends RecyclerView.Adapter {
    @NonNull
    @Override

    // onCreateViewHolder: cria uma gaveta.
    public RecyclerView.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        // 1 Inflar o XML da gaveta
        ConstraintLayout elementoPrincipalXML = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.gavetas, parent, false
        );
        // 1.1 Associar a gaveta a um novo View Holder (o VH controla a atualização dos itens de acordo c/ o objeto q está sendo renderizado)
        DadosAbastecimentoViewHolder gaveta = new DadosAbastecimentoViewHolder(elementoPrincipalXML);
        return gaveta;
    }

    @Override
    // onBindViewHolder: Coloca objetos dentro da gaveta
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DadosAbastecimento DA = DadosAbastecimentoDao.obterInstancia().obterLista().get(position);
        DadosAbastecimentoViewHolder gaveta = (DadosAbastecimentoViewHolder) holder;

        gaveta.atualizaGavetaCObjQChegou(DA);
    }

    @Override
    // Metodo p/ saber o número de itens q tem no Recycler
    public int getItemCount() {
        return  DadosAbastecimentoDao.obterInstancia().obterLista().size();
    }
}
