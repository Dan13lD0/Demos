package com.android.pocketquestions;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class FragmentPrincipal extends Fragment{

    private CardView btnCategoria;
    private CardView btnPergunta;
    private CardView btnAproveitamento;
    private CardView btnAnotacoes;
    private CardView btnAgendamento;
    private CardView btnSair;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main,container,false);
        btnCategoria = (CardView)view.findViewById(R.id.cdCategoria);
        btnCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mViewPager.setCurrentItem(1);
            }
        });

        btnPergunta = (CardView)view.findViewById(R.id.cdPergunta);
        btnPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mViewPager.setCurrentItem(2);
            }
        });

        btnAproveitamento = (CardView)view.findViewById(R.id.cdAproveitamento);
        btnAproveitamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mViewPager.setCurrentItem(3);
            }
        });

        btnAnotacoes = (CardView)view.findViewById(R.id.cdAnotacoes);
        btnAnotacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mViewPager.setCurrentItem(4);
            }
        });

        btnAgendamento = (CardView)view.findViewById(R.id.cdAgendamento);
        btnAgendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mViewPager.setCurrentItem(5);
            }
        });

        btnSair = (CardView)view.findViewById(R.id.cdSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View bview = getLayoutInflater().inflate(R.layout.dialog_message_question,null);

                final Button btnSim = (Button) bview.findViewById(R.id.btnSim) ;
                final Button btnNao = (Button) bview.findViewById(R.id.btnNao) ;

                builder.setView(bview);
                final AlertDialog dialog = builder.create();

                btnSim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        ((Activity)v.getContext()).finish();
                    }
                });

                btnNao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();

            }
        });


        return view;
    }
}
