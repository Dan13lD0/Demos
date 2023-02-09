package com.android.app.devonaodevo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Controle.Funcoes;
import Dal.PagoDal;
import Dto.ContaDto;
import Dto.GrupoMesDto;
import RecyclerViewGroups.PagarHeader;
import RecyclerViewGroups.PagarItens;
import RecyclerViewGroups.PagarItensAdapter;

public class AcPagaConta extends AppCompatActivity {

    private Button btnVoltar;
    private RecyclerView rcListaConta;
    private  PagarItensAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private PagarItensAdapter rcadapter;
    private Thread thCarregaGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_paga_conta);
        btnVoltar = findViewById(R.id.btnVoltar);
        rcListaConta = findViewById(R.id.rcListaPagar);
        rcListaConta.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.pgLoading);
        linearLayout= findViewById(R.id.lnNotFound);


        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DefaultActybit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        rcadapter = CarregaRecycler();
        CarregaRecyclerEvente();

    }

    private void CarregaRecyclerEvente(){

        if (rcadapter.getItemCount() > 0){
            linearLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            rcListaConta.setVisibility(View.VISIBLE);

            rcListaConta.setAdapter(CarregaRecycler());
        }
        else
        {
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            rcListaConta.setVisibility(View.GONE);
        }

    }

    private void DefaultActybit(){
        linearLayout.setVisibility(View.GONE);
        rcListaConta.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private PagarItensAdapter CarregaRecycler(){

        try{
            ArrayList<GrupoMesDto> meses =new PagoDal(this).BuscaListaGrupo();

            ArrayList<PagarHeader> companies = new ArrayList<>();
            ArrayList<PagarItens> products;

            for (GrupoMesDto obj : meses){
                ArrayList<ContaDto> conta = new PagoDal(this).BuscaLista(obj.MesAbreviado);
                products = new ArrayList<>();
                Double totalGeral = 0.0;
                if (conta.size() > 0){
                    for (ContaDto i : conta){
                        products.add(new PagarItens(i.Id,i.Descricao,i.QtdParcela.toString(),i.ParcelaAtual,
                                "0",
                                new Funcoes().RetornaValorString(new DecimalFormat("###,###,###,###,##0.00").format(Double.parseDouble(i.ValorParcela.toString()))),
                                new Funcoes().DateToString(i.DataCompra),
                                new Funcoes().RetornaValorString(new DecimalFormat("###,###,###,###,##0.00").format(Double.parseDouble(i.Valor.toString()))),
                                0.0));
                        if (i.QtdParcela > 0){
                            totalGeral = totalGeral + i.ValorParcela;
                        }
                        else{
                            totalGeral = totalGeral + i.Valor;
                        }
                    }
                    products.add(new PagarItens(Long.parseLong("0"),"","0",0,"","","","",totalGeral));

                }
                companies.add(new PagarHeader(obj.MesExtenso,products));
            }

            adapter = new PagarItensAdapter(companies,this);
        }
        catch(Exception u){
            String resposta = u.getMessage();
        }

        return adapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        rcListaConta.setAdapter(CarregaRecycler());
    }
}