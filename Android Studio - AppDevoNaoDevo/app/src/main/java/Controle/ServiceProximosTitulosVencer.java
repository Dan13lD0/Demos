package Controle;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

import com.android.app.devonaodevo.AcNotificacaoVencer;

public class ServiceProximosTitulosVencer extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        new Funcoes().SendNotification("Lembrete!","Voçê tem contas que vão vencer no proximos dias, fique atento!",this,new Intent(this, AcNotificacaoVencer.class));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
