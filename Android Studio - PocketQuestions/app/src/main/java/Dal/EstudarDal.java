package Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import Dto.EstudarDto;
import Dto.PerguntaDto;


public class EstudarDal {
    private Context context;
    private Conection cn;
    private PerguntaDto objPergunta;
    private EstudarDto objEstudar;
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

    public EstudarDal(Context context) {
        this.context = context;
    }

    public ArrayList<String> RetornaCategoria(){
        cn = new Conection(context);
        cn.OpeneDb();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM \n" +
                "(SELECT DESCRICAO TITULO FROM CATEGORIA \n" +
                "union all\n" +
                "SELECT TITULO FROM LISTA_PERSONALIZADA) X\n" +
                "GROUP BY TITULO\n" +
                "ORDER BY X.TITULO ASC ");

        ArrayList<String>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        lista.add(cursor.getString(cursor.getColumnIndex("TITULO")));
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


    public ArrayList<EstudarDto> RetornaPergunta(String categoria){
        cn = new Conection(context);
        cn.OpeneDb();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT DISTINCT * from\n" +
                "(SELECT * FROM PERGUNTA\n" +
                "union all\n" +
                "SELECT \n" +
                "P.ID_PERGUNTA,\n" +
                "L.TITULO, \n" +
                "P.PERGUNTA,\n" +
                "P.RESPOSTA1,\n" +
                "P.RESPOSTA2,\n" +
                "P.RESPOSTA3,\n" +
                "P.RESPOSTA4,\n" +
                "P.RESPOSTA5,\n" +
                "P.RESPOSTA_CERTA\n" +
                "FROM PERGUNTA P inner JOIN LISTA_PERSONALIZADA L ON P.ID_PERGUNTA = L.ID_PERGUNTA) X \n" +
                "WHERE UPPER(X.CATEGORIA) = '" + categoria.trim().toUpperCase() +"'\n" +
                "ORDER BY X.ID_PERGUNTA DESC");

        ArrayList<EstudarDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        objEstudar = new EstudarDto();

                        objEstudar.IdPergunta = cursor.getLong(cursor.getColumnIndex(fieldId));
                        objEstudar.Categoria = cursor.getString(cursor.getColumnIndex(fieldCategoria));
                        objEstudar.Pergunta = cursor.getString(cursor.getColumnIndex(fieldPergunta));
                        objEstudar.Resposta1 = cursor.getString(cursor.getColumnIndex(fieldResposta1));
                        objEstudar.Resposta2 = cursor.getString(cursor.getColumnIndex(fieldResposta2));
                        objEstudar.Resposta3 = cursor.getString(cursor.getColumnIndex(fieldResposta3));
                        objEstudar.Resposta4 = cursor.getString(cursor.getColumnIndex(fieldResposta4));
                        objEstudar.Resposta5 = cursor.getString(cursor.getColumnIndex(fieldResposta5));
                        objEstudar.RespostaCerta = cursor.getString(cursor.getColumnIndex(fieldRespostaCerta));

                        lista.add(objEstudar);
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

}
