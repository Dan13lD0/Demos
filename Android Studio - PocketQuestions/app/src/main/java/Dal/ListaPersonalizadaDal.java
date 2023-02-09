package Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import Dto.ListaPersonalizadaDto;

public class ListaPersonalizadaDal {

    private String table = "LISTA_PERSONALIZADA";

    private String fieldId = "ID";
    private String fieldTitulo = "TITULO";
    private String fieldIdPergunta = "ID_PERGUNTA";

    private String tableCategoria = "CATEGORIA";

    private String fieldIdCategoria = "ID";
    private String fieldDescricaoCategoria = "DESCRICAO";
    private String fieldStatusCategoria = "STATUS";

    private Context context;
    private Conection cn;

    private ListaPersonalizadaDto obj;

    public ListaPersonalizadaDal(Context context) {
        this.context = context;
    }

    public void  CriarTabelaListaPersonalizada(){
        cn = new Conection(context);
        cn.OpeneDb();;
        cn.CriaTabela(table,fieldId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                fieldTitulo + " TEXT, " +
                fieldIdPergunta + " INTEGER");
        cn.CloseDb();
    }

    public ArrayList<ListaPersonalizadaDto> RetornaListaPersonalizada(){
        cn = new Conection(context);
        cn.OpeneDb();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT " + fieldTitulo + " FROM " + table + " GROUP BY " + fieldTitulo + " ORDER BY " + fieldTitulo + " ASC ");

        ArrayList<ListaPersonalizadaDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        obj = new ListaPersonalizadaDto();
                        obj.Titulo = cursor.getString(cursor.getColumnIndex(fieldTitulo));
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



    public boolean VerificaSeExiteEmCategoria(String titulo){
        cn = new Conection(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM " + tableCategoria + " WHERE  upper(" + fieldDescricaoCategoria + ") = upper('" + titulo.trim().toUpperCase() + "') ");
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

    public boolean VerificaSeExiteEmLista(ListaPersonalizadaDto obj,String tituloAntigo){
        cn = new Conection(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();
        if (!tituloAntigo.trim().equals(obj.Titulo)){
            sql.append("SELECT * FROM " + table + " WHERE  upper(" + fieldTitulo + ") = upper('" + obj.Titulo.trim().toUpperCase() + "') ");
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
        }
        return  false;
    }

    public void Insert(ListaPersonalizadaDto obj){
        cn = new Conection(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();

        cv.put(fieldIdPergunta,obj.IdPergunta);
        cv.put(fieldTitulo, obj.Titulo.toUpperCase());

        cn.Insert(table,cv);
        cn.CloseDb();
    }

    public void Update(ListaPersonalizadaDto obj){
        cn = new Conection(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();
        cv.put(fieldIdPergunta,obj.IdPergunta);
        cv.put(fieldTitulo,obj.Titulo.toUpperCase());
        cn.Update(table,cv, fieldId + " = " + obj.Id);
        cn.CloseDb();
    }

    public void DeletaLista(String titulo){
        cn = new Conection(context);
        cn.OpeneDb();
        cn.Delete(table, fieldTitulo + " = '" + titulo.toUpperCase() + "'");
        cn.CloseDb();
    }
}
