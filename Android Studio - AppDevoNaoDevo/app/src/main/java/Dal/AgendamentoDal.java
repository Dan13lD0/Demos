package Dal;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.Date;

import Controle.Funcoes;

public class AgendamentoDal {
    private Context context;
    private Conexao cn;
    private String table = "AGENDAMENTOS";
    private String fieldAgendamento = "DESCRICAO";
    private String fieldDataAgendamento = "DATAAGENDAMENTO";

    public AgendamentoDal(Context context) {
        this.context = context;
    }

    public void DropTable(){
        cn = new Conexao(context);
        cn.OpeneDb();;
        cn.DropTable(table);
        cn.CloseDb();
    }

    public void  CriarTabelaAgendamento(){
        cn = new Conexao(context);
        cn.OpeneDb();;
        cn.CriaTabela(table,fieldAgendamento + " TEXT," + fieldDataAgendamento + " TEXT");
        cn.CloseDb();
    }

    public boolean VerificaSeJaExisteAgendamento(String tipo, Date data){
        cn = new Conexao(context);
        cn.OpeneDb();
        try{
            Cursor cursor = cn.BuscaLista("SELECT COUNT(*) valor FROM " + table + " WHERE DESCRICAO = '" + tipo +  "' AND DATAAGENDAMENTO = '" + new Funcoes().DateSqliteToString(data) + "'");
            cursor.moveToFirst();
            Long x = cursor.getLong(cursor.getColumnIndex("valor"));
            if(x > 0){
             return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            cn.CloseDb();
        }

        return  false;
    }

    public void Insert(String tipo, Date data){
        cn = new Conexao(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();
        cv.put(fieldAgendamento,tipo);
        cv.put(fieldDataAgendamento,new Funcoes().DateSqliteToString(data));
        cn.Insert(table,cv);
        cn.CloseDb();
    }
}
