package Dal;

import android.content.Context;

public class CriarTabelasDal {

    private Context context;
    private Conexao cn;

    public CriarTabelasDal(Context context) {
        this.context = context;
    }

    private void CriaTabelaConta(){
        new NovaCompraDal(context).CriarTabelaConta();
    }

    private void CriaTabelaAgendamento(){
        new AgendamentoDal(context).CriarTabelaAgendamento();
    }


    private void CriaTabelaPago(){
        new PagoDal(context).CriarTabelaConta();
    }

    private void CriarTabelaLancamento(){
        new PagarDal(context).CriarTabelaConta();
    }

    public void CriarTabelas(){
        CriaTabelaConta();
        CriaTabelaPago();
        CriarTabelaLancamento();
        CriaTabelaAgendamento();
    }
}
