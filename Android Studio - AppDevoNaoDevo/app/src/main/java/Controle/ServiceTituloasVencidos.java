package Controle;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

import com.android.app.devonaodevo.AcNotificacaoVencido;

import java.util.ArrayList;

import Dal.PagoDal;
import Dto.GrupoMesDto;

public class ServiceTituloasVencidos extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        ArrayList<GrupoMesDto> meses =new PagoDal(this).BuscaListaVencidos();
        if (meses.size() > 0){
            new Funcoes().SendNotification("Aviso!","Existem contas com prazo de pagamento ultrapassados.",this,new Intent(this, AcNotificacaoVencido.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
