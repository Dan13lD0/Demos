package Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.pocketquestions.FragmentAgendamento;
import com.android.pocketquestions.FragmentAnotacoes;
import com.android.pocketquestions.FragmentAproveitamento;
import com.android.pocketquestions.FragmentCategoria;
import com.android.pocketquestions.FragmentPergunta;
import com.android.pocketquestions.FragmentPrincipal;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                FragmentPrincipal Pricipal = new FragmentPrincipal();
                return Pricipal;
            case 1:
                FragmentCategoria Categoria = new FragmentCategoria();
                return Categoria;
            case 2:
                FragmentPergunta Pergunta = new FragmentPergunta();
                return Pergunta;
            case 3:
                FragmentAproveitamento Aproveitamento = new FragmentAproveitamento();
                return Aproveitamento;
            case 4:
                FragmentAnotacoes Anotacoes = new FragmentAnotacoes();
                return Anotacoes;
            case 5:
                FragmentAgendamento Agendamento = new FragmentAgendamento();
                return Agendamento;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 6;
    }
}
