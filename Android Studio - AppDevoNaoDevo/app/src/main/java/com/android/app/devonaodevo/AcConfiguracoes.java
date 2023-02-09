package com.android.app.devonaodevo;

import android.app.AlertDialog;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Controle.Funcoes;
import Dal.CriarTabelasDal;
import Dal.ResetarDal;

public class AcConfiguracoes extends AppCompatActivity {
    private Button btnApagar;
    private Button btnNotificar;
    private Button btnNotificarNovo;
    private Button btnNotificarVencer;
    private Button btnNotificarVencido;
    private Button btnVoltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_configuracoes);

        btnApagar = (Button) findViewById(R.id.btnResetar);
        btnNotificar = (Button) findViewById(R.id.btnNotification);
        btnNotificarNovo = (Button) findViewById(R.id.btnNotificationNovo);
        btnNotificarVencer = (Button) findViewById(R.id.btnNotificationVencer);
        btnNotificarVencido = (Button) findViewById(R.id.btnNotificationVencidos);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);

        btnNotificarNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Funcoes().SendNotification("Devo e Não Devo","Não deixe de fazer todos os lançamentos de suas compra, para termos mais controle sobre os nossos gastos.",v.getContext(),new Intent(v.getContext(), AcNovaConta.class));
            }
        });

        btnNotificarVencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Funcoes().SendNotification("Lembrete!","Voçê tem contas que vão vencer no proximos dias, fique atento!",v.getContext(),new Intent(v.getContext(), AcNotificacaoVencer.class));
            }
        });

        btnNotificarVencido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Funcoes().SendNotification("Aviso!","Existem contas com prazo de pagamento ultrapassados.",v.getContext(),new Intent(v.getContext(), AcNotificacaoVencido.class));
            }
        });

        btnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View bview = getLayoutInflater().inflate(R.layout.dialog_sair,null);

                final Button btnSim = (Button) bview.findViewById(R.id.btnSim) ;
                final Button btnNao = (Button) bview.findViewById(R.id.btnNao) ;
                final TextView titulo = (TextView) bview.findViewById(R.id.txtTitle);
                final TextView msg = (TextView) bview.findViewById(R.id.txtMsg);

                titulo.setText("Formatar");
                msg.setText("Deseja apagar informações do banco?");

                builder.setView(bview);
                final AlertDialog dialog = builder.create();

                btnSim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new ResetarDal(v.getContext()).Zerar();
                        new CriarTabelasDal(v.getContext()).CriarTabelas();
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
        });

        btnNotificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             try {

              //new Funcoes().SendNotification("Sucesso!","Voçê conseguiu pagar todas suas dividas.",v.getContext());
                 Notification1();
             }
             catch(Exception i ){
                 String w = i.getMessage();
             }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Notification1(){
        ComponentName serviceName= new ComponentName(getBaseContext(), Controle.ServicoAlarme.class);
        JobInfo jobInfo= new JobInfo.Builder(0, serviceName)
                .setMinimumLatency(Integer.parseInt("60")*1000)
                .setRequiresCharging(false).build();
        JobScheduler scheduler= (JobScheduler) getBaseContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result= scheduler.schedule(jobInfo);
        if(result== JobScheduler.RESULT_SUCCESS)
            Log.d("MainActivity", "Serviço agendado!");
    }


}
