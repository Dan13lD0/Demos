package com.android.app.devonaodevo;

import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.util.Date;

import Controle.Funcoes;
import Dal.AgendamentoDal;
import Dal.CriarTabelasDal;

public class AcPrincipal extends AppCompatActivity {
    private CardView NovaConta;
    private CardView PagarConta;
    private CardView ContasPaga;
    private CardView CardSair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_principal);
        new CriarTabelasDal(this).CriarTabelas();
        NovaConta = findViewById(R.id.cdNovaConta);
        PagarConta = findViewById(R.id.cdPagaConta);
        ContasPaga = findViewById(R.id.cdJaPaguei);
        CardSair = findViewById(R.id.cdSair);

        NovaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),AcNovaConta.class));
            }
        });

        PagarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),AcPagaConta.class));
            }
        });

        ContasPaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),AcPagoConta.class));
            }
        });

        CardSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BotaoPesquisa(v);
            }
        });

        ChamaUsuarioParaLancarNovasCompra();

        ChamaUsuarioParaProximosTituloasVencer();

        ChamaUsuarioParaTituloasVencidos();
    }

    public void BotaoPesquisa(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View bview = getLayoutInflater().inflate(R.layout.dialog_sair,null);
        final Button btnSim = (Button) bview.findViewById(R.id.btnSim) ;
        final Button btnNao = (Button) bview.findViewById(R.id.btnNao) ;
        builder.setView(bview);
        final AlertDialog dialog = builder.create();

        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            dialog.cancel();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.btnConfiguracaoes){

            startActivity(new Intent (this,AcConfiguracoes.class));;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void ChamaUsuarioParaLancarNovasCompra(){

        String day = new Funcoes().getDayNow();
        Date dateNow = null;
        try {
            dateNow = new Funcoes().ToDate(new Funcoes().getDateNow());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(new AgendamentoDal(this).VerificaSeJaExisteAgendamento("NOVACOMPRA",dateNow) == false){
            new AgendamentoDal(this).Insert("NOVACOMPRA",dateNow);
            if (day.trim().equals("01") || day.trim().equals("20") ){
                ComponentName serviceName= new ComponentName(getBaseContext(), Controle.ServiceNovaConta.class);
                JobInfo jobInfo= new JobInfo.Builder(0, serviceName)
                        .setMinimumLatency(Integer.parseInt("3")*86400000)
                        .setRequiresCharging(false).build();
                JobScheduler scheduler= (JobScheduler) getBaseContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                try{
                    int result= scheduler.schedule(jobInfo);
                }
                catch(Exception e){
                    String j = e.getMessage();
                }
            }
        }
    }

    private void ChamaUsuarioParaProximosTituloasVencer(){

        String day = new Funcoes().getDayNow();
        Date dateNow = null;
        try {
           dateNow = new Funcoes().ToDate(new Funcoes().getDateNow());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(new AgendamentoDal(this).VerificaSeJaExisteAgendamento("VENCER",dateNow) == false){
            new AgendamentoDal(this).Insert("VENCER",dateNow);
            if (day.trim().equals("01") || day.trim().equals("05") || day.trim().equals("10") || day.trim().equals("15") || day.trim().equals("20") || day.trim().equals("25") || day.trim().equals("30") ){
                ComponentName serviceName= new ComponentName(getBaseContext(), Controle.ServiceProximosTitulosVencer.class);
                JobInfo jobInfo= new JobInfo.Builder(0, serviceName)
                        .setMinimumLatency(Integer.parseInt("4")*86400000)
                        .setRequiresCharging(false).build();
                JobScheduler scheduler= (JobScheduler) getBaseContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                try{
                    int result= scheduler.schedule(jobInfo);
                }
                catch(Exception e){
                    String j = e.getMessage();
                }
            }
        }
    }

    private void ChamaUsuarioParaTituloasVencidos(){

        String day = new Funcoes().getDayNow();
        Date dateNow = null;
        try {
            dateNow = new Funcoes().ToDate(new Funcoes().getDateNow());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(new AgendamentoDal(this).VerificaSeJaExisteAgendamento("VENCIDO",dateNow) == false){
            new AgendamentoDal(this).Insert("VENCIDO",dateNow);
            if (day.trim().equals("01") || day.trim().equals("05") || day.trim().equals("10") || day.trim().equals("15") || day.trim().equals("20") || day.trim().equals("25") || day.trim().equals("30") ){
                ComponentName serviceName= new ComponentName(getBaseContext(), Controle.ServiceTituloasVencidos.class);
                JobInfo jobInfo= new JobInfo.Builder(0, serviceName)
                        .setMinimumLatency(Integer.parseInt("4")*86400000)
                        .setRequiresCharging(false).build();
                JobScheduler scheduler= (JobScheduler) getBaseContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                try{
                    int result= scheduler.schedule(jobInfo);
                }
                catch(Exception e){
                    String j = e.getMessage();
                }

            }
        }
     }
}
