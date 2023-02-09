package com.android.pocketquestions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import Dal.AnotacaoDal;
import Dto.AnotacaoDto;
import Recycler.RecyclerAnotacoes;

public class FragmentAgendamento extends Fragment{

    private RecyclerView recyclerView;
    private Button btnVoltar;
    private FloatingActionButton btnNovo;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_agendamento,container,false);
        btnNovo = (FloatingActionButton)view.findViewById(R.id.flNovo);
        btnVoltar = (Button)view.findViewById(R.id.btnVoltar);
        recyclerView = (RecyclerView)view.findViewById(R.id.rcAnotacoes);
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),AcCadAltAnotacao.class));
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mViewPager.setCurrentItem(0);
            }
        });
        context = view.getContext();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        CarregaRecycler(context);
    }

    private void  CarregaRecycler(Context context){
        ArrayList<AnotacaoDto> list = new AnotacaoDal(context).RetornaAnotacoes();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerAnotacoes categoria = new RecyclerAnotacoes(list,context);
        recyclerView.setAdapter(categoria);

    }
}