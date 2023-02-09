package Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;

import Dto.PerguntaDto;


public class PerguntaDal {
    private Context context;
    private Conection cn;
    private PerguntaDto objPergunta;

    private String table = "PERGUNTA";

    private String fieldId = "ID_PERGUNTA";
    private String fieldCategoria = "CATEGORIA";
    private String fieldPergunta = "PERGUNTA";
    private String fieldResposta1 = "RESPOSTA1";
    private String fieldResposta2 = "RESPOSTA2";
    private String fieldResposta3 = "RESPOSTA3";
    private String fieldResposta4 = "RESPOSTA4";
    private String fieldResposta5 = "RESPOSTA5";
    private String fieldRespostaCerta = "RESPOSTA_CERTA";

    public void  CriarTabelaPergunta(){
        cn = new Conection(context);
        cn.OpeneDb();;
        cn.CriaTabela(table,fieldId + " INTEGER PRIMARY KEY AUTOINCREMENT," + fieldCategoria + " TEXT, " + fieldPergunta + " TEXT, " + fieldResposta1 + " TEXT, " + fieldResposta2 + " TEXT, " + fieldResposta3 + " TEXT, "+ fieldResposta4 + " TEXT, " + fieldResposta5 + " TEXT, " + fieldRespostaCerta + " TEXT");
        cn.CloseDb();
    }

    public PerguntaDal(Context context) {
        this.context = context;
    }

    public ArrayList<String> RetornaCategoria(){
        cn = new Conection(context);
        cn.OpeneDb();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM CATEGORIA ORDER BY DESCRICAO ASC ");

        ArrayList<String>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        lista.add(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
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


    public ArrayList<PerguntaDto> RetornaPerguntas(){
        cn = new Conection(context);
        cn.OpeneDb();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM " + table + " ORDER BY " + fieldId + " DESC ");

        ArrayList<PerguntaDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        objPergunta = new PerguntaDto();

                        objPergunta.IdPergunta = cursor.getLong(cursor.getColumnIndex(fieldId));
                        objPergunta.Categoria = cursor.getString(cursor.getColumnIndex(fieldCategoria));
                        objPergunta.Pergunta = cursor.getString(cursor.getColumnIndex(fieldPergunta));
                        objPergunta.Resposta1 = cursor.getString(cursor.getColumnIndex(fieldResposta1));
                        objPergunta.Resposta2 = cursor.getString(cursor.getColumnIndex(fieldResposta2));
                        objPergunta.Resposta3 = cursor.getString(cursor.getColumnIndex(fieldResposta3));
                        objPergunta.Resposta4 = cursor.getString(cursor.getColumnIndex(fieldResposta4));
                        objPergunta.Resposta5 = cursor.getString(cursor.getColumnIndex(fieldResposta5));
                        objPergunta.RespostaCerta = cursor.getString(cursor.getColumnIndex(fieldRespostaCerta));

                        lista.add(objPergunta);
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


    public void Insert(PerguntaDto obj){
        cn = new Conection(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();

        cv.put(fieldCategoria,obj.Categoria.toUpperCase());
        cv.put(fieldPergunta,obj.Pergunta.toUpperCase());
        cv.put(fieldResposta1,obj.Resposta1.toUpperCase());
        cv.put(fieldResposta2,obj.Resposta2.toUpperCase());
        cv.put(fieldResposta3,obj.Resposta3.toUpperCase());
        cv.put(fieldResposta4,obj.Resposta4.toUpperCase());
        cv.put(fieldResposta5,obj.Resposta5.toUpperCase());
        cv.put(fieldRespostaCerta,obj.RespostaCerta.toUpperCase());

        cn.Insert(table,cv);
        cn.CloseDb();
    }

    public void Update(PerguntaDto obj){
        cn = new Conection(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();

        cv.put(fieldCategoria,obj.Categoria.toUpperCase());
        cv.put(fieldPergunta,obj.Pergunta.toUpperCase());
        cv.put(fieldResposta1,obj.Resposta1.toUpperCase());
        cv.put(fieldResposta2,obj.Resposta2.toUpperCase());
        cv.put(fieldResposta3,obj.Resposta3.toUpperCase());
        cv.put(fieldResposta4,obj.Resposta4.toUpperCase());
        cv.put(fieldResposta5,obj.Resposta5.toUpperCase());
        cv.put(fieldRespostaCerta,obj.RespostaCerta.toUpperCase());

        cn.Update(table,cv, fieldId + " = " + obj.IdPergunta);
        cn.CloseDb();
    }

    public void DeletaListaPergunta(Long idPergunta){
        cn = new Conection(context);
        cn.OpeneDb();
        cn.Delete("PERGUNTA", "ID_PERGUNTA  = " + idPergunta + "");
        cn.CloseDb();
    }

    public void DeletaListaPersonalizada(Long idPergunta){
        cn = new Conection(context);
        cn.OpeneDb();
        cn.Delete("LISTA_PERSONALIZADA", "ID_PERGUNTA  = " + idPergunta + "");
        cn.CloseDb();
    }
}
