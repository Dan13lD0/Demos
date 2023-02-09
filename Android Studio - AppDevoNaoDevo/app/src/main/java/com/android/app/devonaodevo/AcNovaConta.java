package com.android.app.devonaodevo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import Controle.Funcoes;
import Dal.NovaCompraDal;
import Dto.ContaDto;
import RecyclerViews.RecyclerNovaCompra;

public class AcNovaConta extends AppCompatActivity {
    private TextView txtTotal;
    private Button btnVoltar;
    private FloatingActionButton btnNovo;
    private RecyclerView rcContas;
    private NovaCompraDal novaCompraDal;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    public void CarregaRecycler(){
        progressBar.setVisibility(View.VISIBLE);
        List<ContaDto> x = ListaContas();
        if (x.size() > 0){
            progressBar.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            rcContas.setVisibility(View.VISIBLE);
            new Funcoes().CarregaRecyclerViewSimple(rcContas,this,new RecyclerNovaCompra(x,this));
            Double total = 0.0;
            for (ContaDto o : x){
                total = total + o.Valor;
            }

            if (total > 0){
                txtTotal.setText(new Funcoes().RetornaValorString(new DecimalFormat("###,###,###,###,###.00").format(total)));
            }
            else {
                txtTotal.setText("0,00");
            }
        }
        else{
                rcContas.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                txtTotal.setText("0,00");
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_nova_conta);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnNovo = (FloatingActionButton) findViewById(R.id.btnNovo);
        rcContas = (RecyclerView) findViewById(R.id.rcNovaConta);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        progressBar = (ProgressBar) findViewById(R.id.pgLoading);
        linearLayout = (LinearLayout) findViewById(R.id.lnNotFound);

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(),AcCadAltConta.class),0);
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        CarregaRecycler();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        DefaultActybit();
        CarregaRecycler();
    }

    private List<ContaDto> ListaContas() {
        novaCompraDal = new NovaCompraDal(this);
        ArrayList<ContaDto> ListaContas = novaCompraDal.BuscaLista(new ContaDto());
        return ListaContas;
    }

    private void DefaultActybit(){
        linearLayout.setVisibility(View.GONE);
        rcContas.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

}
