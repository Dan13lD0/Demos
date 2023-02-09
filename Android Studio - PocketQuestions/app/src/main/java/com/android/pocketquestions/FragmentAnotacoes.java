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

import Dal.ListaPersonalizadaDal;
import Dto.ListaPersonalizadaDto;
import Recycler.RecyclerListaPersonalizada;

public class FragmentAnotacoes extends Fragment{

    private FloatingActionButton btnNovo;
    private Button btnVoltar;
    private RecyclerView recyclerView;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_anotacoes,container,false);
        btnVoltar = (Button)view.findViewById(R.id.btnVoltar);
        btnNovo = (FloatingActionButton)view.findViewById(R.id.flNovo);
        recyclerView = (RecyclerView)view.findViewById(R.id.rcListaPersonalizada);
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),AcCadAltListaPersonalizada.class));
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
        CarregaRecyclerView(context);
    }

    private void CarregaRecyclerView(Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<ListaPersonalizadaDto> list = new ListaPersonalizadaDal(context).RetornaListaPersonalizada();
        RecyclerListaPersonalizada lista = new RecyclerListaPersonalizada(list,context);
        recyclerView.setAdapter(lista);
    }
}