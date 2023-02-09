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

import Dal.CategoriaDal;
import Dto.CategoriaDto;
import Recycler.RecyclerCategoria;

public class FragmentCategoria extends Fragment{

    private Button btnVoltar;
    private FloatingActionButton btnNovo;
    private RecyclerView recyclerView;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_categoria,container,false);
        btnVoltar = (Button)view.findViewById(R.id.btnVoltar);
        btnNovo= (FloatingActionButton)view.findViewById(R.id.flNovo);
        recyclerView = (RecyclerView)view.findViewById(R.id.rcCategoria);
        context = view.getContext();


        CarregaRecycler(context);



        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context,AcCadAltCategoria.class),0);
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mViewPager.setCurrentItem(0);
            }
        });

        return view;
    }

    private void  CarregaRecycler(Context context){
        ArrayList<CategoriaDto> list = new CategoriaDal(context).RetornaCategoria();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerCategoria categoria = new RecyclerCategoria(list,context);
        recyclerView.setAdapter(categoria);

    }

    @Override
    public void onStart() {
        super.onStart();
        CarregaRecycler(context);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //CarregaRecycler(context);
    }
}