package com.example.appcalcautonomiaveiculosv2.Modelo;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class DadosAbastecimento extends RealmObject {
    @PrimaryKey
    private String idAbastecimento;
    private int quilometragemAtual;
    private Double litrosAbastecidos;

    @Ignore
    private Calendar dataAbastecimento;

    private Date dataQRealmSuporta;
    private String posto;
    private String caminhoFotografia;

    public DadosAbastecimento() {
        idAbastecimento = UUID.randomUUID().toString();
    }

    public String getIdAbastecimento() {
        return idAbastecimento;
    }


    public int getQuilometragemAtual() {
        return quilometragemAtual;
    }

    public void setQuilometragemAtual(int quilometragemAtual) {
        this.quilometragemAtual = quilometragemAtual;
    }

    public Double getLitrosAbastecidos() {
        return litrosAbastecidos;
    }

    public void setLitrosAbastecidos(Double litrosAbastecidos) {
        this.litrosAbastecidos = litrosAbastecidos;
    }

    public Calendar getDataAbastecimento() {
        if (dataQRealmSuporta != null) {
            dataAbastecimento = Calendar.getInstance();
            dataAbastecimento.setTime(dataQRealmSuporta);
        }
        return dataAbastecimento;
    }

    public void setDataAbastecimento(Calendar dataAbastecimento) {
        this.dataQRealmSuporta = dataAbastecimento.getTime();
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }

    public String getCaminhoFotografia() {
        return caminhoFotografia;
    }

    public void setCaminhoFotografia(String caminhoFotografia) {
        this.caminhoFotografia = caminhoFotografia;
    }
}
