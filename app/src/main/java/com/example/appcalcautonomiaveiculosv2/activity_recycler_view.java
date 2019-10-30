package com.example.appcalcautonomiaveiculosv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appcalcautonomiaveiculosv2.Adaptador.DadosAbastecimentoAdaptador;
import com.example.appcalcautonomiaveiculosv2.Modelo.DadosAbastecimentoDao;

import javax.annotation.Nullable;

// ESSE CARA É O CARA Q CUIDA DO RV. ELE BASICAMENTE, RESPECTIVAMENTE:
//      - PEGA O RV;
//      - CRIA UMA NOVA INSTANCIA DO ADAP P/ DIZER PRA INTERFACE DO RV Q ELE (ADAP) É O CARA RESPONSÁVEL POR COLOCAR OBJS NO RV;
//      - ESPECIFICA P/ O RV COMO Q DEVE SER EXIBIDO OS OBJETOS (UM EM BAIXO DO OUTRO);
//      - COLOCA O RV NO ADAP;
//      - ITENCAO P/ ABRIR FORMULÁRIO;
//      - ITENCAO P/ ALTERAR DADOS INSERIDOS NO FORMULÁRIO;
//      - "onActivityResult()", MÉTODO P/ PERMITIR Q AO ABRIR UMA NOVA ACTIVITY A PARTIR DA ACTIVITY ATUAL, ESSA ACTIVITY ATUAL RECEBER DADOS DA ACTIVITY Q FOI ABERTA, APÓS SEU TÉRMINO.
public class activity_recycler_view extends AppCompatActivity {

    private DadosAbastecimentoAdaptador adap;
    private RecyclerView rV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        rV = findViewById(R.id.rVDadosAbastecimento);
        adap = new DadosAbastecimentoAdaptador();
        rV.setLayoutManager(new LinearLayoutManager(this));
        rV.setAdapter(adap);
    }

    public void adcAbastecimento (View v) {
        Intent itencaoAbrirFormAbastecimento = new Intent(this, activity_formulario.class);
        startActivityForResult(itencaoAbrirFormAbastecimento, 1);
    }

    public void alterarAbastecimento (View v, String idAbastecimento) {
        Intent intencaoAbrirFormAbastecimentoPAlterar = new Intent(this, activity_formulario.class);
        intencaoAbrirFormAbastecimentoPAlterar.putExtra("idAbaste", idAbastecimento);
        startActivityForResult(intencaoAbrirFormAbastecimentoPAlterar, 1);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1) {
            if (resultCode == 200) {
                int posicaoItem = data.getIntExtra("posicaoObjEditado", -1);
                adap.notifyItemChanged(posicaoItem);
                rV.smoothScrollToPosition(posicaoItem);
            } else if (resultCode == 201) {
                Toast.makeText(this, "Dados do abastecimento inseridos com sucesso.", Toast.LENGTH_LONG).show();
                int posicaoItem = DadosAbastecimentoDao.obterInstancia().obterLista().size()-1;
                adap.notifyItemInserted(posicaoItem);
                rV.smoothScrollToPosition(posicaoItem);
            } else if (resultCode == 202) {
                Toast.makeText(this, "Abastecimento excluído com sucesso", Toast.LENGTH_LONG).show();
                int posicaoItem = data.getIntExtra("posicaoObjExcluido", -1);
                adap.notifyItemRemoved(posicaoItem);
            }
        }
    }
}
