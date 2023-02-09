package com.android.pocketquestions;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import Dal.ListaPersonalizadaDal;
import Dal.PerguntaDal;
import Dal.PerguntaListaDal;
import Dto.ListaPersonalizadaDto;
import Dto.PerguntaListaDto;
import Funcoes.Funcoes;
import Recycler.RecyclerPergunta;
import Recycler.RecyclerPerguntaLista;

public class AcCadAltListaPersonalizada extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnVoltar;
    private Button btnConfirmar;
    private EditText txtTitulo;
    private Long id;
    private String tituloAntigo;
    private ListaPersonalizadaDto objAlterar = new ListaPersonalizadaDto();
    private ListaPersonalizadaDto obj;

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
        setContentView(R.layout.activity_ac_cad_alt_lista_personalizada);

        recyclerView = (RecyclerView)findViewById(R.id.rcListaPerguntas);
        btnVoltar = (Button)findViewById(R.id.btnVoltar);
        btnConfirmar = (Button)findViewById(R.id.btnConfirmar);
        txtTitulo = (EditText)findViewById(R.id.txtTitulo);

        Bundle bundle = getIntent().getExtras();
        if((bundle != null) && (bundle.containsKey("Alterar"))) {
            objAlterar = (ListaPersonalizadaDto) bundle.getSerializable("Alterar");
            id = objAlterar.Id;
            txtTitulo.setText(objAlterar.Titulo);
            tituloAntigo = objAlterar.Titulo;
            CarregaRecycler(objAlterar.Titulo);
            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    objAlterar.Id = id;
                    objAlterar.Titulo = txtTitulo.getText().toString().trim();
                     if (objAlterar.Titulo.length() > 0 ){
                         if (new ListaPersonalizadaDal(v.getContext()).VerificaSeExiteEmCategoria(objAlterar.Titulo) == false){
                             if (new ListaPersonalizadaDal(v.getContext()).VerificaSeExiteEmLista(objAlterar,tituloAntigo) == false){

                                 new ListaPersonalizadaDal(v.getContext()).DeletaLista(objAlterar.Titulo);
                                 for (PerguntaListaDto i : RecyclerPerguntaLista.listaConfirma){
                                     obj = new ListaPersonalizadaDto();
                                     obj.Id = objAlterar.Id;
                                     obj.Titulo = objAlterar.Titulo;
                                     obj.IdPergunta =i.IdPergunta;
                                     new ListaPersonalizadaDal(v.getContext()).Insert(obj);
                                 }
                                 MessageFinaliza("Sucesso","Lista Personalizada alterada com sucesso!");
                             }
                             else
                             {
                                 MessageAviso("Aviso","Este titulo já exite em lista personalizada!");
                             }
                         }
                         else{
                             MessageAviso("Aviso","Este titulo já exite em categorias!");
                         }
                     }
                     else{
                         MessageAviso("Aviso","Preencha os campos obrigatorios!");
                     }
                }
            });
        }
        else{
            CarregaRecycler("");
            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    objAlterar.Id = id;
                    objAlterar.Titulo = txtTitulo.getText().toString().trim();
                    if (objAlterar.Titulo.length() > 0 ){
                        if (new ListaPersonalizadaDal(v.getContext()).VerificaSeExiteEmCategoria(objAlterar.Titulo) == false){
                            if (new ListaPersonalizadaDal(v.getContext()).VerificaSeExiteEmLista(objAlterar,"") == false){

                                for (PerguntaListaDto i : RecyclerPerguntaLista.listaConfirma){
                                    obj = new ListaPersonalizadaDto();
                                    obj.Id = objAlterar.Id;
                                    obj.Titulo = objAlterar.Titulo;
                                    obj.IdPergunta = i.IdPergunta;
                                    new ListaPersonalizadaDal(v.getContext()).Insert(obj);
                                }
                                MessageFinaliza("Sucesso","Lista Personalizada criada com sucesso!");
                            }
                            else
                            {
                                MessageAviso("Aviso","Este titulo já exite em lista personalizada!");
                            }
                        }
                        else{
                            MessageAviso("Aviso","Este titulo já exite em categorias!");
                        }
                    }
                    else{
                        MessageAviso("Aviso","Preencha os campos obrigatorios!");
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



    private void  CarregaRecycler(String titulo){
        ArrayList<PerguntaListaDto> list = new PerguntaListaDal(this).RetornaPerguntas(titulo);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerPerguntaLista pergunta = new RecyclerPerguntaLista(list,this);
        recyclerView.setAdapter(pergunta);
    }
}
