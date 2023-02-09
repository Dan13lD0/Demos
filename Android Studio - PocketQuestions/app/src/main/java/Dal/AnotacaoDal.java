package Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import Dto.AnotacaoDto;

public class AnotacaoDal {
    private String table = "ANOTACOES";

    private String fieldId = "ID";
    private String fieldTitulo = "TITULO";
    private String fieldDescricao = "DESCRICAO";

    private Context context;
    private Conection cn;

    private AnotacaoDto obj;

    public AnotacaoDal(Context context) {
        this.context = context;
    }

    public void  CriarTabelaAnotacoes(){
        cn = new Conection(context);
        cn.OpeneDb();;
        cn.CriaTabela(table,fieldId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    fieldTitulo + " TEXT, " +
                                    fieldDescricao + " TEXT");
        cn.CloseDb();
    }

    public ArrayList<AnotacaoDto> RetornaAnotacoes(){
        cn = new Conection(context);
        cn.OpeneDb();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM " + table + " ORDER BY " + fieldId + " DESC ");

        ArrayList<AnotacaoDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        obj = new AnotacaoDto();
                        obj.Id = cursor.getLong(cursor.getColumnIndex(fieldId));
                        obj.Titulo = cursor.getString(cursor.getColumnIndex(fieldTitulo));
                        obj.Descrcicao = cursor.getString(cursor.getColumnIndex(fieldDescricao));

                        lista.add(obj);
                    }
                    while(cursor.moveToNext());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            cn.CloseDb();
        }

        return  lista;
    }

    public void Insert(AnotacaoDto obj){
        cn = new Conection(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();
        cv.put(fieldTitulo,obj.Titulo.toUpperCase());
        cv.put(fieldDescricao,obj.Descrcicao.toUpperCase());
        cn.Insert(table,cv);
        cn.CloseDb();
    }

    public void Update(AnotacaoDto obj){
        cn = new Conection(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();
        cv.put(fieldTitulo,obj.Titulo.toUpperCase());
        cv.put(fieldDescricao,obj.Descrcicao.toUpperCase());
        cn.Update(table,cv, fieldId + " = " + obj.Id);
        cn.CloseDb();
    }
}
