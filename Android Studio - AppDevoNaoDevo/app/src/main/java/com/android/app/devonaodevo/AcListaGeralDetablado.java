package com.android.app.devonaodevo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import Controle.Funcoes;
import Dal.ListaContasGeralDal;
import Dto.GrupoMesDto;
import Dto.ListaGeralDetalhadaDto;
import Interfece.ILoadMore;
import RecyclerHistoricoDetalhado.DetalhadoHeader;
import RecyclerHistoricoDetalhado.DetalhadoItens;
import RecyclerHistoricoDetalhado.DetalhadoItensAdapter;

public class AcListaGeralDetablado extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnSair;
    private TextView titulo;
    private ListaContasGeralDal listaContasGeralDal;
    private  final int VIEW_TYPE_ITEM=0,VIEW_TYPE_LOADING=1;
    ILoadMore loadMore;
    boolean isLoading;
    int visibleThreshold = 5;
    int lastVisibleItem,totalItemCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_lista_geral_detablado);

        recyclerView = (RecyclerView)findViewById(R.id.rcDetalhes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        titulo = (TextView)findViewById(R.id.txtTitulo);
        btnSair = (Button)findViewById(R.id.btnVoltar);

        Bundle bundle = getIntent().getExtras();
        if((bundle != null) && (bundle.containsKey("Alterar"))){
            GrupoMesDto alterar = (GrupoMesDto)bundle.getSerializable("Alterar");
            CarregaRecycler(alterar);
        }
        else{
            finish();
        }




        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void CarregaRecycler(GrupoMesDto obj) {
        titulo.setText(obj.MesExtenso + " - " + obj.MesAbreviado);

        String selectmes = "";

        if (obj.MesAbreviado.contains("JANEIRO")){
            selectmes =obj.MesAbreviado.replace("JANEIRO ","01/");
        }
        else if (obj.MesAbreviado.contains("FEVEREIRO")){
            selectmes =obj.MesAbreviado.replace("FEVEREIRO ","02/");
        }
        else if (obj.MesAbreviado.contains("MARÇO")){
            selectmes =obj.MesAbreviado.replace("MARÇO ","03/");
        }
        else if (obj.MesAbreviado.contains("ABRIL")){
            selectmes =obj.MesAbreviado.replace("ABRIL ","04/");
        }
        else if (obj.MesAbreviado.contains("MAIO")){
            selectmes =obj.MesAbreviado.replace("MAIO ","05/");
        }
        else if (obj.MesAbreviado.contains("JUNHO")){
            selectmes =obj.MesAbreviado.replace("JUNHO ","06/");
        }
        else if (obj.MesAbreviado.contains("JULHO")){
            selectmes =obj.MesAbreviado.replace("JULHO ","07/");
        }
        else if (obj.MesAbreviado.contains("AGOSTO")){
            selectmes =obj.MesAbreviado.replace("AGOSTO ","08/");
        }
        else if (obj.MesAbreviado.contains("SETEMBRO")){
            selectmes =obj.MesAbreviado.replace("SETEMBRO ","09/");
        }
        else if (obj.MesAbreviado.contains("OUTUBRO")){
            selectmes =obj.MesAbreviado.replace("OUTUBRO ","10/");
        }
        else if (obj.MesAbreviado.contains("NOVEMBRO")){
            selectmes =obj.MesAbreviado.replace("NOVEMBRO ","11/");
        }
        else{
            selectmes =obj.MesAbreviado.replace("DEZEMBRO ","12/");
        }
        ArrayList<String> mes = null;

        if(obj.MesExtenso.trim().equals("PAGOS")){
            mes = new ListaContasGeralDal(this).ListaMesPagos(obj.MesExtenso , selectmes);
        }

        if(obj.MesExtenso.trim().equals("NÃO PAGOS")){
            mes = new ListaContasGeralDal(this).ListaMesDetalhados(obj.MesExtenso , selectmes);
        }

        if(obj.MesExtenso.trim().equals("PROXIMOS VENCIMENTOS")){
            mes = new ListaContasGeralDal(this).ListaMesDetalhados(obj.MesExtenso , selectmes);
        }

        if(obj.MesExtenso.trim().equals("VENCIDOS")){
            mes = new ListaContasGeralDal(this).ListaMesDetalhados(obj.MesExtenso , selectmes);
        }
        ArrayList<DetalhadoHeader> companies = new ArrayList<>();

        ArrayList<DetalhadoItens> itensGroup;
        ArrayList<ListaGeralDetalhadaDto> itens = null;
        for(String m : mes){
            String index = "I";
            Integer count = 1;
            itens = new ArrayList<>();
            itensGroup = new ArrayList<>();

            if(obj.MesExtenso.trim().equals("PAGOS")){
                itens = new ListaContasGeralDal(this).DetalhadoContasPagos(obj.MesExtenso,m);
            }

            if(obj.MesExtenso.trim().equals("NÃO PAGOS")){
                itens = new ListaContasGeralDal(this).DetalhadoContas(obj.MesExtenso,m);
            }

            if(obj.MesExtenso.trim().equals("PROXIMOS VENCIMENTOS")){
                itens = new ListaContasGeralDal(this).DetalhadoContas(obj.MesExtenso,m);
            }

            if(obj.MesExtenso.trim().equals("VENCIDOS")){
                itens = new ListaContasGeralDal(this).DetalhadoContas(obj.MesExtenso,m);
            }

            for (ListaGeralDetalhadaDto a : itens){

                if(itens.size() == 1){
                    index = "T";
                }
                else{
                    if (itens.size() == count){
                        index = "F";
                    }
                }

                itensGroup.add(new DetalhadoItens(a.IdCompra,a.NroParcela,new Funcoes().DateToString(a.DataCompra),
                        a.Descricao,
                        a.Parcelado,
                        new Funcoes().DateToString(a.DataVencimento),
                        new Funcoes().DateToString(a.DataPagamento),
                        a.ValorOriginal,a.ValorPagamento,
                        a.TotalPagamento,a.Entrada,index,obj.MesAbreviado,obj.MesExtenso));
                count = count +1;

            }
            Date titulo = null;
            try {
                titulo = new Funcoes().ToDateSqLite(m);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            companies.add(new DetalhadoHeader(new Funcoes().DateToString(titulo),itensGroup));
        }

        DetalhadoItensAdapter adapter = new DetalhadoItensAdapter(companies,this);

        recyclerView.setAdapter(adapter);
    }


}
