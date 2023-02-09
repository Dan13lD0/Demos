package Dal;

import android.content.Context;

public class ResetarDal {

    private Context context;
    private Conexao cn;

    public ResetarDal(Context context) {
        this.context = context;
    }

    private void DropTableConta(){
        new NovaCompraDal(context).DropTable();
    }

    private void DropTableAgendamento(){
        new AgendamentoDal(context).DropTable();
    }

    private void DropTablePago(){
        new PagoDal(context).DropTable();
    }

    private void DropTablePagar(){
        new PagarDal(context).DropTable();
    }

    public void Zerar(){
        DropTableConta();
        DropTablePago();
        DropTablePagar();
        DropTableAgendamento();
    }
}
