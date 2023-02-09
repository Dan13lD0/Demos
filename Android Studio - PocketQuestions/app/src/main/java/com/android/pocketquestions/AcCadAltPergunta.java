package com.android.pocketquestions;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Dal.PerguntaDal;
import Dto.PerguntaDto;
import Funcoes.Funcoes;
import SpinnerCustons.SpinnerCuston;

import static android.widget.Toast.LENGTH_LONG;

public class AcCadAltPergunta extends AppCompatActivity {

    private Spinner spCategoria;
    private Button btnVoltar;
    private Button btnConfirmar;
    private MultiAutoCompleteTextView txtPergunta;
    private MultiAutoCompleteTextView txtResposta1;
    private MultiAutoCompleteTextView txtResposta2;
    private MultiAutoCompleteTextView txtResposta3;
    private MultiAutoCompleteTextView txtResposta4;
    private MultiAutoCompleteTextView txtResposta5;
    private RadioButton rbResposta1;
    private RadioButton rbResposta2;
    private RadioButton rbResposta3;
    private RadioButton rbResposta4;
    private RadioButton rbResposta5;
    private PerguntaDto obj;
    private ArrayList<String> list = new ArrayList<>();
    private Long id;

    private void MessageAviso(String titulo,String msg){
        View view = getLayoutInflater().inflate(R.layout.dialog_message,null);
        Button acao = view.findViewById(R.id.btnAcao);
        TextView txtTitulo = view.findViewById(R.id.txtTitulo);
        TextView txtDescricao = view.findViewById(R.id.txtDescricao);
        acao.setText("ok");
        txtTitulo.setText(titulo);
        txtDescricao.setText(msg);

        final AlertDialog dialog = new Funcoes().MessageBox(view);
        acao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void MessageFinaliza(String titulo,String msg){
        View view = getLayoutInflater().inflate(R.layout.dialog_message,null);
        Button acao = view.findViewById(R.id.btnAcao);
        TextView txtTitulo = view.findViewById(R.id.txtTitulo);
        TextView txtDescricao = view.findViewById(R.id.txtDescricao);
        acao.setText("ok");
        txtTitulo.setText(titulo);
        txtDescricao.setText(msg);

        final AlertDialog dialog = new Funcoes().MessageBox(view);
        acao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_cad_alt_pergunta);

        spCategoria = (Spinner) findViewById(R.id.spCategoria);
        btnVoltar   = (Button) findViewById(R.id.btnVoltar);
        btnConfirmar= (Button) findViewById(R.id.btnConfirmar);
        txtPergunta = (MultiAutoCompleteTextView) findViewById(R.id.txtPergunta);
        txtResposta1 = (MultiAutoCompleteTextView) findViewById(R.id.txtResposta1);
        txtResposta2 = (MultiAutoCompleteTextView) findViewById(R.id.txtResposta2);
        txtResposta3 = (MultiAutoCompleteTextView) findViewById(R.id.txtResposta3);
        txtResposta4 = (MultiAutoCompleteTextView) findViewById(R.id.txtResposta4);
        txtResposta5 = (MultiAutoCompleteTextView) findViewById(R.id.txtResposta5);

        rbResposta1 = (RadioButton) findViewById(R.id.rbResposta1);
        rbResposta2 = (RadioButton) findViewById(R.id.rbResposta2);
        rbResposta3 = (RadioButton) findViewById(R.id.rbResposta3);
        rbResposta4 = (RadioButton) findViewById(R.id.rbResposta4);
        rbResposta5 = (RadioButton) findViewById(R.id.rbResposta5);

        CarregaSpinnerCategoria();
        id = Long.parseLong("0");
        Bundle bundle = getIntent().getExtras();
        if((bundle != null) && (bundle.containsKey("Alterar"))){
            obj = (PerguntaDto) bundle.getSerializable("Alterar");
            id = obj.IdPergunta;
            spCategoria.setSelection(list.indexOf(obj.Categoria));
            txtPergunta.setText(obj.Pergunta);
            txtResposta1.setText(obj.Resposta1);
            txtResposta2.setText(obj.Resposta2);
            txtResposta3.setText(obj.Resposta3);
            txtResposta4.setText(obj.Resposta4);
            txtResposta5.setText(obj.Resposta5);


            if (obj.RespostaCerta.trim().equals("1"))
            {
                rbResposta1.setChecked(true);
            }
            else if (obj.RespostaCerta.trim().equals("2"))
            {
                rbResposta2.setChecked(true);
            }
            else if (obj.RespostaCerta.trim().equals("3"))
            {
                rbResposta3.setChecked(true);
            }
            else if (obj.RespostaCerta.trim().equals("4"))
            {
                rbResposta4.setChecked(true);
            }
            else{
                rbResposta5.setChecked(true);
            }

            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(VerificaCamposPreenchidos()){
                        new PerguntaDal(v.getContext()).Update(CarregaObj());
                        MessageFinaliza("Sucesso","Pergunta alterada com sucesso!");
                    }
                    else{
                        MessageAviso("Aviso","Favor informar os campos necessários, lembrando que precisamos de pelo menos 2 respostas!");
                    }

                }
            });
        }
        else{

            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(VerificaCamposPreenchidos()){
                        new PerguntaDal(v.getContext()).Insert(CarregaObj());
                        MessageFinaliza("Sucesso","Pergunta incluida com sucesso!");
                    }
                    else{
                        MessageAviso("Aviso","Favor informar os campos necessários, lembrando que precisamos de pelo menos 2 respostas!");
                    }

                }
            });
        }

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    private void CarregaSpinnerCategoria(){
        list.add("Selecione");
        for(String a : new PerguntaDal(this).RetornaCategoria()){
            list.add(a);
        }
        SpinnerCuston cusnton = new SpinnerCuston(list,this);
        spCategoria.setAdapter(cusnton);
    }

    private PerguntaDto CarregaObj(){
        obj = new PerguntaDto();
        obj.IdPergunta = id;
        obj.Categoria = list.get(spCategoria.getSelectedItemPosition());
        obj.Pergunta = txtPergunta.getText().toString().trim();
        obj.Resposta1 = txtResposta1.getText().toString().trim();
        obj.Resposta2 = txtResposta2.getText().toString().trim();
        obj.Resposta3 = txtResposta3.getText().toString().trim();
        obj.Resposta4 = txtResposta4.getText().toString().trim();
        obj.Resposta5 = txtResposta5.getText().toString().trim();

        if (rbResposta1.isChecked()){
            obj.RespostaCerta = "1";
        }
        else  if (rbResposta2.isChecked()){
            obj.RespostaCerta = "2";
        }
        else  if (rbResposta3.isChecked()){
            obj.RespostaCerta = "3";
        }
        else  if (rbResposta4.isChecked()){
            obj.RespostaCerta = "4";
        }
        else
        {
            obj.RespostaCerta = "5";
        }


        return obj;
    }

    private boolean VerificaCamposPreenchidos(){

        if (list.get(spCategoria.getSelectedItemPosition()).equals("Selecione")){
            return false;
        }

        if (txtPergunta.getText().toString().trim().length() <= 0){
            return false;
        }

        int respostas = 0;

        if (txtResposta1.getText().toString().trim().length() > 0){
            respostas++;
        }


        if (txtResposta2.getText().toString().trim().length() > 0){
            respostas++;
        }

        if (txtResposta3.getText().toString().trim().length() > 0){
            respostas++;
        }
        if (txtResposta4.getText().toString().trim().length() > 0){
            respostas++;
        }

        if (txtResposta5.getText().toString().trim().length() > 0){
            respostas++;
        }

        if (respostas <= 1){
            return false;
        }

        respostas = 0;

        boolean verifica = false;

        verifica = rbResposta1.isChecked();
        if (verifica == true){
            if (txtResposta1.getText().toString().trim().length() > 0){
                respostas++;
                verifica = false;
            }
        }

        verifica = rbResposta2.isChecked();
        if (verifica == true){
            if (txtResposta2.getText().toString().trim().length() > 0){
                respostas++;
                verifica = false;
            }
        }

        verifica = rbResposta3.isChecked();
        if (verifica == true){
            if (txtResposta3.getText().toString().trim().length() > 0){
                respostas++;
                verifica = false;
            }
        }

        verifica = rbResposta4.isChecked();
        if (verifica == true){
            if (txtResposta4.getText().toString().trim().length() > 0){
                respostas++;
                verifica = false;
            }
        }

        verifica = rbResposta5.isChecked();
        if (verifica == true){
            if (txtResposta5.getText().toString().trim().length() > 0){
                respostas++;
                verifica = false;
            }
        }

        if(respostas < 1){
            return false;
        }
        return true;
    }
}
