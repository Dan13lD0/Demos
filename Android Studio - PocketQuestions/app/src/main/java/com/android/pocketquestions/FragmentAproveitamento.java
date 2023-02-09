package com.android.pocketquestions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Dal.EstudarDal;
import Dto.EstudarDto;
import Funcoes.Funcoes;
import SpinnerCustons.SpinnerCuston;

public class FragmentAproveitamento extends Fragment{

    private ArrayList<String> list;
    private Button btnIniciar;
    private Spinner spCategoria;
    private Button btnVoltar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_aproveitamento,container,false);
        btnIniciar = (Button)view.findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoria = list.get(spCategoria.getSelectedItemPosition());
                if(!categoria.trim().equals("Selecione")){
                    ArrayList<EstudarDto> lista = new EstudarDal(v.getContext()).RetornaPergunta(categoria);

                    if(lista.size() > 0){
                        Intent it = new Intent(v.getContext(), AcIniciarTeste.class);
                        it.putExtra("Perguntas",lista);
                        startActivity(it);
                    }
                    else{
                        MessageAviso("Aviso","Esta categoria não tem perguntas vinculadas!");
                    }

                }
                else{
                   MessageAviso("Aviso","Selecione uma categoria para iniciar o teste!");
                }

            }
        });

        btnVoltar = (Button)view.findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mViewPager.setCurrentItem(0);
            }
        });
        spCategoria = (Spinner) view.findViewById(R.id.spCategoria);
        CarregaSpinnerCategoria(view.getContext());
        return view;
    }

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

    @Override
    public void onStart() {
        super.onStart();
        spCategoria.setSelection(0);
    }

    private void CarregaSpinnerCategoria(Context context){
        list =  new ArrayList<>();
        list.add("Selecione");
        for(String a : new EstudarDal(context).RetornaCategoria()){
            list.add(a);
        }
        SpinnerCuston cusnton = new SpinnerCuston(list,((Activity)context));
        spCategoria.setAdapter(cusnton);
    }
}