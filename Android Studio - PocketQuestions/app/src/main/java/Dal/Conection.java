package Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conection extends SQLiteOpenHelper {

    private static final String nome_db = "POCKETQUESTION";
    private static final int versao = 1;
    private static final String patch = "/data/user/0/Dal/database/" + nome_db ;
    private Context xcontext;
    private SQLiteDatabase cn;

    public Conection(Context context) {
        super(context, nome_db , null, versao);
        this.xcontext = context;
        cn = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean CriaTabela(String tabela,String campos){
        String query = "CREATE TABLE IF NOT EXISTS " + tabela + "(" + campos + ")";

        try{
            cn.execSQL(query);
            return  true;
        }catch(Exception e ){
            e.printStackTrace();
        }
        return  false;
    }

    public boolean DropTable(String tabela){

        String query = "DROP TABLE IF EXISTS " + tabela ;

        try{
            cn.execSQL(query);
            return  true;
        }catch(Exception e ){
            e.printStackTrace();
        }
        return  false;
    }

    public boolean Insert(String tabela, ContentValues contentValues){
        try {
            cn.insert(tabela,null,contentValues);
            return  true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean Update(String tabela, ContentValues contentValues,String where){
        try {
            cn.update(tabela,contentValues,where,null);
            return  true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean Delete(String tabela,String where){
        try {
            cn.delete(tabela,where,null);
            return  true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Cursor BuscaLista(String Query){
        Cursor cursor = null;
        try{
            cursor = cn.rawQuery(Query,null);
            return cursor;
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return cursor;
    }

    public void OpeneDb(){
        if (!cn.isOpen()){
            cn = xcontext.openOrCreateDatabase(patch,SQLiteDatabase.OPEN_READWRITE,null);
        }
    }

    public void CloseDb(){
        cn.close();
    }

}

