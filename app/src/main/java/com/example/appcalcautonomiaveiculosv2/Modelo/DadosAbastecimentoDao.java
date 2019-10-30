package com.example.appcalcautonomiaveiculosv2.Modelo;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class DadosAbastecimentoDao {
    private ArrayList<DadosAbastecimento> bD;

    public ArrayList<DadosAbastecimento> obterLista() {
        //Realm realmbD = Realm.getDefaultInstance();
        Realm teste = Realm.getDefaultInstance();
        RealmResults lista = teste.where(DadosAbastecimento.class).findAll();

        bD.clear();
        bD.addAll(teste.copyFromRealm(lista));
        return bD;
    }

    public void adcNaLista (DadosAbastecimento DA) {
        Realm realmbD = Realm.getDefaultInstance();
        realmbD.beginTransaction();
        realmbD.copyToRealm(DA);
        realmbD.commitTransaction();
    }

    public int atualizaNaLista (DadosAbastecimento DA) {
        Realm realmbD = Realm.getDefaultInstance();
        realmbD.beginTransaction();
        realmbD.copyToRealm(DA);
        realmbD.commitTransaction();

        // Percorrer p/ colocar o id do objeto q foi/esta sendo atualizado na lista
        for (int i = 0; i < bD.size(); i++) {
            if (bD.get(i).getIdAbastecimento().equals(DA.getIdAbastecimento())) {
                bD.set(i, DA);
                return i;
            }
        }
        return - 1; // Significa q n encontrou nda p/ atualizar
    }

    public int excluirNaLista(DadosAbastecimento DA) {
        Realm realmbD = Realm.getDefaultInstance();
        realmbD.beginTransaction();
        realmbD.where(DadosAbastecimento.class).equalTo("idAbastecimento", DA.getIdAbastecimento()).findFirst().deleteFromRealm();
        realmbD.commitTransaction();

        // Percorrer p/ remover o id que foi/esta sendo excluido da lista
        for (int i = 0; i <bD.size(); i++) {
            if (bD.get(i).getIdAbastecimento().equals(DA.getIdAbastecimento())) {
                bD.remove(i);
                return i;
            }
        }
        return -1; // Se n encontrou nada p/ excluir.
    }

    public DadosAbastecimento obterObjPeloId (String idAbastecimento) {
        Realm realmbD = Realm.getDefaultInstance();
        realmbD.beginTransaction();
        DadosAbastecimento DA = realmbD.copyFromRealm(realmbD.where(DadosAbastecimento.class).equalTo("idAbastecimento", idAbastecimento).findFirst());
        realmbD.commitTransaction();
        return DA;
    }

    private static DadosAbastecimentoDao INSTANCIA;

    public static DadosAbastecimentoDao obterInstancia(){

        if (INSTANCIA == null) {
            INSTANCIA = new DadosAbastecimentoDao();
        }
        return INSTANCIA;
    }

    private DadosAbastecimentoDao() {
        // Garantir q o ArrayList(bD) nunca fique null p/ q se tenha como retorno do msm um ArrayList vazio e não um objeto null.
        bD = new ArrayList<DadosAbastecimento>();
    }
}
