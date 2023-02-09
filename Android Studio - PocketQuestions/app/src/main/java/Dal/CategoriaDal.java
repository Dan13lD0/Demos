package Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import Dto.CategoriaDto;

public class CategoriaDal {

    private String table = "CATEGORIA";

    private String fieldId = "ID";
    private String fieldDescricao = "DESCRICAO";
    private String fieldStatus = "STATUS";

    private Context context;
    private Conection cn;

    private CategoriaDto obj;

    public CategoriaDal(Context context) {
        this.context = context;
    }

    public void  CriarTabelaCategoria(){
        cn = new Conection(context);
        cn.OpeneDb();;
        cn.CriaTabela(table,fieldId + " INTEGER PRIMARY KEY AUTOINCREMENT," + fieldDescricao + " TEXT, " + fieldStatus + " TEXT");
        cn.CloseDb();
    }

    public ArrayList<CategoriaDto> RetornaCategoria(){
        cn = new Conection(context);
        cn.OpeneDb();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM " + table + " ORDER BY " + fieldDescricao + " ASC ");

        ArrayList<CategoriaDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        obj = new CategoriaDto();
                        obj.Id = cursor.getLong(cursor.getColumnIndex(fieldId));
                        obj.Descricao = cursor.getString(cursor.getColumnIndex(fieldDescricao));
                        obj.Status = cursor.getString(cursor.getColumnIndex(fieldStatus));

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

    public boolean VerificaSeExite(CategoriaDto obj){
        cn = new Conection(context);
        cn.OpeneDb();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM " + table + " WHERE " + fieldId + " != " + String.valueOf(obj.Id) + " AND upper(" + fieldDescricao + ") = upper('" + obj.Descricao.trim().toUpperCase() + "') ");
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
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

    public void Insert(CategoriaDto obj){
        cn = new Conection(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();

        cv.put(fieldDescricao,obj.Descricao.toUpperCase());
        cv.put(fieldStatus, "0");

        cn.Insert(table,cv);
        cn.CloseDb();
    }

    public void Update(CategoriaDto obj){
        cn = new Conection(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();
        cv.put(fieldDescricao,obj.Descricao.toUpperCase());
        cv.put(fieldStatus,obj.Status.toUpperCase());
        cn.Update(table,cv, fieldId + " = " + obj.Id);
        cn.CloseDb();
    }

    public boolean RetornaPerguntas(String categoria){
        cn = new Conection(context);
        cn.OpeneDb();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM PERGUNTA WHERE CATEGORIA = '" + categoria.toUpperCase() + "'");

        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
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

    public void DeletaCategoria(String categoria){
        cn = new Conection(context);
        cn.OpeneDb();
        cn.Delete(table, fieldDescricao + " = '" + categoria.toUpperCase() + "'");
        cn.CloseDb();
    }

    public void DeletaListaPergunta(String categoria){
        cn = new Conection(context);
        cn.OpeneDb();
        cn.Delete("PERGUNTA", "CATEGORIA  = '" + categoria.toUpperCase() + "'");
        cn.CloseDb();
    }
}
