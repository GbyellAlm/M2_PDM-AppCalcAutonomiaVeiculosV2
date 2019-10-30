package com.example.appcalcautonomiaveiculosv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appcalcautonomiaveiculosv2.Modelo.DadosAbastecimento;
import com.example.appcalcautonomiaveiculosv2.Modelo.DadosAbastecimentoDao;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import fr.ganfra.materialspinner.MaterialSpinner;

public class activity_formulario extends AppCompatActivity {
    private DadosAbastecimento objDadosAbastecimento;
    private String idAbastecimento;
    private TextInputEditText etDataAbastecimento;
    private TextInputEditText etLAbastecidos;
    private TextInputEditText etKMTotVeiculo;
    private Spinner spinner;
    private MaterialSpinner spPostos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etDataAbastecimento = findViewById(R.id.etDataAbastecimento);
        etDataAbastecimento.setKeyListener(null);
        etLAbastecidos = findViewById(R.id.etLAbastecidos);
        etKMTotVeiculo = findViewById(R.id.etKMTotVeiculo);
        spPostos = findViewById(R.id.spPostos);

        String[] opcoesPosto = getResources().getStringArray(R.array.opcoes_posto);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcoesPosto);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPostos.setAdapter(adapter);

        idAbastecimento = getIntent().getStringExtra("idAbaste");
        // Esse "if" é a criação de abastecimento.
        if (idAbastecimento == null) {
            objDadosAbastecimento = new DadosAbastecimento();

            Button btnExcluir = findViewById(R.id.btnExcluir);
            btnExcluir.setVisibility(View.INVISIBLE);
            // Esse "else" é a edição de um abastecimento (iniciando a edição).
        } else {
            objDadosAbastecimento = DadosAbastecimentoDao.obterInstancia().obterObjPeloId(idAbastecimento);

            DateFormat formatadorData = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            String dataFormatadaSelecionada = formatadorData.format(objDadosAbastecimento.getDataAbastecimento().getTime());
            etDataAbastecimento.setText(dataFormatadaSelecionada);
            etLAbastecidos.setText(String.valueOf(objDadosAbastecimento.getLitrosAbastecidos()));
            etKMTotVeiculo.setText(String.valueOf(objDadosAbastecimento.getQuilometragemAtual()));

            for (int i = 0; i < spPostos.getAdapter().getCount(); i++) {
                if (spPostos.getAdapter().getItem(i).toString() == objDadosAbastecimento.getPosto()) {
                    spPostos.setSelection(i+1);
                    break;
                }
            }
        }
    }

    public void salvarAbastecimento (View v) {
        /*Editable sla = etKMTotVeiculo.getText();
        Editable sla2 = etLAbastecidos.getText();

        int x = Integer.parseInt(sla.toString());
        int y = Integer.parseInt(sla2.toString());

        int conta = x / y;

        Intent intecaoMostrarResultNaMain = new Intent(this, MainActivity.class);
        intecaoMostrarResultNaMain.putExtra("resultado", conta);
        startActivity(intecaoMostrarResultNaMain);

        objDadosAbastecimento.setLitrosAbastecidos(Double.parseDouble(etLAbastecidos.getText().toString()));
        objDadosAbastecimento.setQuilometragemAtual(Integer.parseInt(etKMTotVeiculo.getText().toString()));
        objDadosAbastecimento.setPosto(spPostos.getSelectedItem().toString());*/

        // Esse "if" significa q está salvando.
        if (idAbastecimento == null) {
            DadosAbastecimentoDao.obterInstancia().adcNaLista(objDadosAbastecimento);
            setResult(201);
            // Esse "else" significa q está editando (finalizando a edição).
        } else {
            int posicaoObj = DadosAbastecimentoDao.obterInstancia().atualizaNaLista(objDadosAbastecimento);
            Intent intencaoFecharActivityFormulario = new Intent();
            intencaoFecharActivityFormulario.putExtra("posicaoObjEditado", posicaoObj);
            setResult(200, intencaoFecharActivityFormulario);
        }
        finish();
    }

    public void selecionarData (View v) {
        Calendar dataPadrao = objDadosAbastecimento.getDataAbastecimento();
        if (dataPadrao == null) {
            dataPadrao = Calendar.getInstance();
        }

        DatePickerDialog diagPPegarData = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    // onDateSet é um método chamado qdo a pessoa seleciona uma data
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar dataSelecionada = Calendar.getInstance();
                        dataSelecionada.set(year, month, dayOfMonth);
                        objDadosAbastecimento.setDataAbastecimento(dataSelecionada);
                        // o DateFormat formata datas de acordo c/ a localização da pessoa.
                        DateFormat formatador = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                        String dtaSelecioFormatada = formatador.format(dataSelecionada.getTime());
                        etDataAbastecimento.setText(dtaSelecioFormatada);
                    }
                },
                dataPadrao.get(Calendar.YEAR),
                dataPadrao.get(Calendar.MONTH),
                dataPadrao.get(Calendar.DAY_OF_MONTH)

        );
        diagPPegarData.show();
    }

    public void excluir (View view) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir abastecimento")
                .setMessage("Você tem certeza que quer excluir esse abastecimento?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int posiObj = DadosAbastecimentoDao.obterInstancia().excluirNaLista(objDadosAbastecimento);
                        Intent intencaoFecharActivityFormulario = new Intent();
                        intencaoFecharActivityFormulario.putExtra("posiObjExcluido", posiObj);
                        setResult(202, intencaoFecharActivityFormulario);

                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    String caminhoFto = null;

    private File criarArqSalvarFto() throws IOException {
        String nmFto = UUID.randomUUID().toString();
        File diretorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File fto = File.createTempFile(nmFto, ".jpg", diretorio);
        caminhoFto = fto.getAbsolutePath();
        return fto;
    }

    public void openCamera (View v) {
        Intent intencaoAbrirCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File arqFto = null;
        try {
            arqFto = criarArqSalvarFto();
        } catch (IOException expt) {
            Toast.makeText(this, "Não foi possível criar arquivo p/ foto.", Toast.LENGTH_LONG).show();
        }
        if (arqFto != null) {
            Uri ftoUri = FileProvider.getUriForFile(this, "com.example.appcalcautonomiaveiculosv2.fileprovider", arqFto);
            intencaoAbrirCamera.putExtra(MediaStore.EXTRA_OUTPUT, ftoUri);
            startActivityForResult(intencaoAbrirCamera, 1);
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                objDadosAbastecimento.setCaminhoFotografia(caminhoFto);
                atualiFotoTela();
            }
        }
    }

    private void atualiFotoTela() {
        if (objDadosAbastecimento.getCaminhoFotografia() != null) {
            ImageView ivFto = findViewById(R.id.ivFoto);
            ivFto.setImageURI(Uri.parse(objDadosAbastecimento.getCaminhoFotografia()));
        }
    }
}
