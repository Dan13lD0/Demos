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

import Dal.PerguntaDal;
import Dto.PerguntaDto;
import Recycler.RecyclerPergunta;

public class FragmentPergunta extends Fragment{

    private Button btnVoltar;
    private FloatingActionButton btnNovo;
    private RecyclerView recyclerView;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pergunta,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rcPergunda);
        btnNovo  = (FloatingActionButton)view.findViewById(R.id.flNovo);
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),AcCadAltPergunta.class));
            }
        });


        btnVoltar = (Button)view.findViewById(R.id.btnVoltar);
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
        ArrayList<PerguntaDto> list = new PerguntaDal(context).RetornaPerguntas();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerPergunta pergunta = new RecyclerPergunta(list,context);
        recyclerView.setAdapter(pergunta);

    }
}