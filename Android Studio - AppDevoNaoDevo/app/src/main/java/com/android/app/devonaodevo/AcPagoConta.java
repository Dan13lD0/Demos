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

import java.util.ArrayList;

import Dal.ListaContasGeralDal;
import Dto.GrupoMesDto;
import RecyclerGroupPago.PagoHeader;
import RecyclerGroupPago.PagoItens;
import RecyclerGroupPago.PagoItensAdapter;

public class AcPagoConta extends AppCompatActivity {

    private Button btnVoltar;
    private RecyclerView recLista;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_pago_conta);

        recLista = findViewById(R.id.rcPago);
        recLista.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.pgLoading);
        linearLayout = (LinearLayout) findViewById(R.id.lnNotFound);

       CarregaRecycler();

        btnVoltar = findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
    }

    private void CarregaRecycler(){
        ArrayList<PagoHeader> companies = new ArrayList<>();

        ArrayList<PagoItens> pagos = new ArrayList<>();

        ArrayList<GrupoMesDto> ListPagos = new ListaContasGeralDal(this).BuscaListaMesPagos();

        if (ListPagos.size() > 0) {
            for (GrupoMesDto p : ListPagos) {
                pagos.add(new PagoItens(p.MesExtenso,"Pagos"));
            }
            companies.add(new PagoHeader("Pagos", pagos));
        }


        ArrayList<PagoItens> npagos = new ArrayList<>();
        ArrayList<GrupoMesDto> ListNPagos = new ListaContasGeralDal(this).BuscaListaMesNaoPagos();
        if (ListNPagos.size() > 0) {
            for (GrupoMesDto p : ListNPagos) {
                npagos.add(new PagoItens(p.MesExtenso,"Não Pagos"));
            }
            companies.add(new PagoHeader("Não Pagos", npagos));
        }


        ArrayList<PagoItens> proximos = new ArrayList<>();

        ArrayList<GrupoMesDto> ListProximos = new ListaContasGeralDal(this).BuscaListaMesProximosVencimentos();
        if (ListProximos.size() > 0) {
            for (GrupoMesDto p : ListProximos) {
                proximos.add(new PagoItens(p.MesExtenso,"Proximos Vencimentos"));
            }
            companies.add(new PagoHeader("Proximos Vencimentos", proximos));
        }
        ArrayList<PagoItens> venceu = new ArrayList<>();
        ArrayList<GrupoMesDto> ListVencidos = new ListaContasGeralDal(this).BuscaListaMesVencidos();
        if (ListVencidos.size() > 0) {
            for (GrupoMesDto p : ListVencidos) {
                venceu.add(new PagoItens(p.MesExtenso,"Vencidos"));
            }
            companies.add(new PagoHeader("Vencidos", venceu));
        }


        PagoItensAdapter adapter = new PagoItensAdapter(companies,this);
        if (adapter.getItemCount() > 0){
            progressBar.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            recLista.setVisibility(View.VISIBLE);
            recLista.setAdapter(adapter);
        }
        else{
            recLista.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        CarregaRecycler();
    }

    private void DefaultActybit(){
        linearLayout.setVisibility(View.GONE);
        recLista.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
}