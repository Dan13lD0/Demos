package Controle;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

import com.android.app.devonaodevo.AcNovaConta;

public class ServiceNovaConta extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {

        new Funcoes().SendNotification("Devo e Não Devo","Não deixe de fazer todos os lançamentos de suas compra, para termos mais controle sobre os nossos gastos.",this,new Intent(this, AcNovaConta.class));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
