package Dal;

import android.content.ContentValues;
import android.content.Context;

import Controle.Funcoes;
import Dto.PagarDto;

public class PagarDal {

    private String tablePago = "LANCAMENTO";

    private String fieldIdCompra = "IDCOMPRA";
    private String fieldNroParcela = "NROPARCELA";
    private String fieldDataPagamento = "DATAPAGAMENTO";
    private String fieldValorPagamento = "VALORPAGAMENTO";

    private Context context;
    private Conexao cn;

    private PagarDto pagarDto;

    public PagarDal(Context context) {
        this.context = context;
    }

    public void  CriarTabelaConta(){
        cn = new Conexao(context);
        cn.OpeneDb();;
        cn.CriaTabela(tablePago,fieldIdCompra + " INTEGER, "  + fieldNroParcela + " INTEGER," + fieldDataPagamento + " DATE," + fieldValorPagamento + " REAL ");
        cn.CloseDb();
    }

    public void DropTable(){
        cn = new Conexao(context);
        cn.OpeneDb();;
        cn.DropTable(tablePago);
        cn.CloseDb();
    }

    public void Update(PagarDto obj){
        cn = new Conexao(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();
        cv.put("STATUS","INATIVO");
        cn.Update("PAGO",cv," IDCONTA = " + obj.IdCompra.toString() + " AND PARCELAATUAL = " + obj.NroParcela);
        cn.CloseDb();
    }

    public void Insert(PagarDto obj){
        cn = new Conexao(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();
        cv.put(fieldIdCompra,obj.IdCompra);
        cv.put(fieldNroParcela,obj.NroParcela);
        cv.put(fieldDataPagamento, new Funcoes().DateSqliteToString(obj.DataPagamento));
        cv.put(fieldValorPagamento,obj.ValorPagamento);


        try{
            cn.Insert(tablePago,cv);
        }
        catch(Exception t){
            String we = t.getMessage();

        }
        cn.CloseDb();
    }
}
