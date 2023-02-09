package Dal;

import android.content.Context;

public class CriarTabelasDal {
    private Context context;

    public CriarTabelasDal(Context context) {
        this.context = context;
    }

    public void CriarTabelas(){
        TabelaCategoria();
        TabelaPergunta();
        TabelaAnotacoes();
        TabelaListaPersonalizada();
        TabelaPerguntaTemp();
    }

    private void TabelaPerguntaTemp(){
        new PerguntaListaDal(context).CriarTabelaPerguntaTemp();
    }

    private void TabelaCategoria(){
        new CategoriaDal(context).CriarTabelaCategoria();
    }

    private void TabelaPergunta(){
        new PerguntaDal(context).CriarTabelaPergunta();
    }

    private  void TabelaAnotacoes(){
        new AnotacaoDal(context).CriarTabelaAnotacoes();
    }

    private void TabelaListaPersonalizada(){
        new ListaPersonalizadaDal(context).CriarTabelaListaPersonalizada();
    }
}
