package com.example.appcalcautonomiaveiculosv2.Adaptador;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcalcautonomiaveiculosv2.Modelo.DadosAbastecimento;
import com.example.appcalcautonomiaveiculosv2.R;
import com.example.appcalcautonomiaveiculosv2.activity_recycler_view;

import java.text.DateFormat;

// ESSE CARA É O CARA Q FICA RESPONSÁVEL POR MANTER UMA GAVETA SÓ (RELAÇÃO 1 PRA 1: UM VH P/ UMA GAVETA, 1 GAVETA P/ UM VH).
public class DadosAbastecimentoViewHolder extends RecyclerView.ViewHolder {
    private TextView tvTotKmVeiculo;
    private TextView tvLAbastecidos;
    private TextView tvDataAbastecimento;
    //private Spinner spPostos;
    private String idAbastecimento;
    private ConstraintLayout clPai;

    public DadosAbastecimentoViewHolder(@NonNull View itemView) {
        super(itemView);

        // Da linha 28 até a linha 33: adicionamento de evento de clique no elemento principal da gaveta.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((activity_recycler_view) v.getContext()).alterarAbastecimento(v, idAbastecimento);
            }
        });

        tvDataAbastecimento = itemView.findViewById(R.id.tvData);
        tvLAbastecidos = itemView.findViewById(R.id.tvLAbastecidos);
        tvTotKmVeiculo = itemView.findViewById(R.id.tvTotKmVeiculo);
        //spPostos = itemView.findViewById(R.id.)...spinnerQtaNoFormulario...
        clPai = (ConstraintLayout) itemView;
    }

    // Método q atualiza os tv
    public void atualizaGavetaCObjQChegou (DadosAbastecimento DA) {
        idAbastecimento = DA.getIdAbastecimento();

        // OBS: As linhas 47, 49 e 50 do código transformam a data em uma data legível p/ o usu de acordo c/ o idioma q o mesmo está usando no device.

        // * Essa 1a etapa aq significa pegar o contexto da aplicação p/ q se possa obter o DateFormat do device. Esse "tvLAbastecidos.getContext()" significa exatamente isso (pegar o contexto da aplicação). - Foi utilizado um atributo qlqr p/ isso (tvLAbastecidos), pq? PQ SIM!
        DateFormat formatador = android.text.format.DateFormat.getDateFormat(tvLAbastecidos.getContext());
        // * O método format faz a transformação p/ uma data legível. O "DA.getDataAbastecimento" retorna um Calendar e dele é feito um "getTime()" p/ retornar um date.
        String dataFormatada = formatador.format(DA.getDataAbastecimento().getTime());
        tvDataAbastecimento.setText(dataFormatada);

        String castDblLAbasteEmStr = String.valueOf(DA.getLitrosAbastecidos());
        tvLAbastecidos.setText(castDblLAbasteEmStr);

        String castIntTotKMVeiculoEmStr = Integer.toString(DA.getQuilometragemAtual());
        tvTotKmVeiculo.setText(castIntTotKMVeiculoEmStr);
    }

    /*public String getId() {
        return idAbastecimento;
    }

    public void setIdAbastecimento(String idAbastecimento) {
        this.idAbastecimento = idAbastecimento;
    }*/
}
