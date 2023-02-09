package com.android.pocketquestions;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import Dal.AnotacaoDal;
import Dto.AnotacaoDto;
import Funcoes.Funcoes;

public class AcCadAltAnotacao extends AppCompatActivity {
    private EditText txtTitulo;
    private MultiAutoCompleteTextView txtAnotacao;
    private Button btnVoltar;
    private Button btnConfirmar;
    private AnotacaoDto objAlterar = new AnotacaoDto();
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
        setContentView(R.layout.activity_ac_cad_alt_anotacao);
        txtAnotacao = (MultiAutoCompleteTextView)findViewById(R.id.txtAnotacoes);
        txtTitulo = (EditText)findViewById(R.id.txtTitulo);
        btnVoltar = (Button)findViewById(R.id.btnVoltar);
        btnConfirmar = (Button)findViewById(R.id.btnConfirmar);

        Bundle bundle = getIntent().getExtras();
        if((bundle != null) && (bundle.containsKey("Alterar"))) {
            objAlterar = (AnotacaoDto) bundle.getSerializable("Alterar");
            id = objAlterar.Id;
            txtTitulo.setText(objAlterar.Titulo);
            txtAnotacao.setText(objAlterar.Descrcicao);
            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(VerificaCampos() == true){

                        new AnotacaoDal(v.getContext()).Update(CarregaObjeto());
                        MessageFinaliza("Sucesso","Anotação Alterada com sucesso!");
                    }
                    else
                    {
                        MessageAviso("Aviso","Preencha os campos obrigatórios!");
                    }
                }
            });
        }
        else{
            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(VerificaCampos() == true){
                        new AnotacaoDal(v.getContext()).Insert(CarregaObjeto());
                        MessageFinaliza("Sucesso","Anotação incluida com sucesso!");
                    }
                    else
                    {
                        MessageAviso("Aviso","Preencha os campos obrigatórios!");
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

    private boolean VerificaCampos(){

        if (txtTitulo.getText().toString().trim().length() <= 0){
            return false;
        }

        if (txtAnotacao.getText().toString().trim().length() <= 0){
            return false;
        }

        return true;
    }

    private AnotacaoDto CarregaObjeto(){
        objAlterar.Id = id;
        objAlterar.Titulo = txtTitulo.getText().toString();
        objAlterar.Descrcicao = txtAnotacao.getText().toString();

        return  objAlterar;
    }
}
