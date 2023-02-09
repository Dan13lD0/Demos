package Controle;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

public class ServicoAlarme extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        new Funcoes().SendNotification("Sucesso!","Voçê conseguiu pagar todas suas dividas.",this,new Intent());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
