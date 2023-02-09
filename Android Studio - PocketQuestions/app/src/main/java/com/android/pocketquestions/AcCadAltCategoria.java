package com.android.pocketquestions;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import Dal.CategoriaDal;
import Dto.CategoriaDto;
import Funcoes.Funcoes;

public class AcCadAltCategoria extends AppCompatActivity {


    private EditText txtDescricao;
    private CategoriaDto obj;
    private CategoriaDto objAlterar = new CategoriaDto();
    private Button btnVoltar;
    private Button btnConfirmar;
    private Switch swStatus;


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
        setContentView(R.layout.activity_ac_cad_alt_categoria);

        txtDescricao = (EditText)findViewById(R.id.txtDescricao);
        btnConfirmar = (Button)findViewById(R.id.btnConfirmar);
        btnVoltar = (Button)findViewById(R.id.btnVoltar);
        swStatus = (Switch)findViewById(R.id.swStatus);

        swStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    objAlterar.Status  = "0";
                    swStatus.setText("Ativo");
                }
                else{
                    objAlterar.Status  = "1";
                    swStatus.setText("Inativo");
                }
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if((bundle != null) && (bundle.containsKey("Alterar"))){
            objAlterar = (CategoriaDto)bundle.getSerializable("Alterar");
            txtDescricao.setText(objAlterar.Descricao);
            swStatus.setVisibility(View.VISIBLE);
            if(objAlterar.Status.equals("0")){
                swStatus.setChecked(true);
                swStatus.setText("Ativo");
            }
            else{
                swStatus.setChecked(false);
                swStatus.setText("Inativo");
            }

            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    objAlterar.Descricao = new Funcoes().toInitCap(txtDescricao.getText().toString().trim());
                    if (objAlterar.Descricao.length() > 0){
                        if (new CategoriaDal(v.getContext()).VerificaSeExite(objAlterar) == false){
                            new CategoriaDal(v.getContext()).Update(objAlterar);
                            MessageFinaliza("Sucesso","Categoria atualizada com sucesso!");
                        }
                        else
                        {
                            MessageAviso("Aviso","Categoria já existe!");
                        }
                    }
                    else{
                        MessageAviso("Aviso","Preencha os campos obrigatórios!");
                    }
                }
            });
        }
        else{
            swStatus.setVisibility(View.INVISIBLE);
            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    objAlterar.Id = Long.parseLong("0");
                    objAlterar.Descricao = new Funcoes().toInitCap(txtDescricao.getText().toString().trim());
                    if (objAlterar.Descricao.length() > 0){
                        if (new CategoriaDal(v.getContext()).VerificaSeExite(objAlterar) == false){
                            new CategoriaDal(v.getContext()).Insert(objAlterar);
                            MessageFinaliza("Sucesso","Categoria inserida com sucesso!");
                        }
                        else
                        {
                            MessageAviso("Aviso","Categoria já existe!");
                        }
                    }
                    else{
                        MessageAviso("Aviso","Preencha os campos obrigatórios!");
                    }
                }
            });
        }
    }
}
