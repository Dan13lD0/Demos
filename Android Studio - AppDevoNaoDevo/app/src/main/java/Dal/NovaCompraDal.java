package Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import Controle.Funcoes;
import Dto.ContaDto;

public class NovaCompraDal {
    private final String table = "CONTA";
    private final String tablePago= "PAGO";
    private final String tableLancamento= "LANCAMENTO";

    private final String fieldId = "ID";
    private final String fieldDescricao = "DESCRICAO";
    private final String fieldDataCompra = "DATACOMPRA";
    private final String fieldQuantidade = "QUANTIDADE";
    private final String fieldEntrada = "ENTRADA";
    private final String fieldValorParcela = "VALORPARCELA";
    private final String fieldValor = "VALOR";
    private final String fieldUsuario = "USUARIO";
    private final String fieldStatus = "STATUS";

    private ContaDto contaDto;
    private Context context;
    private Conexao cn;

    public NovaCompraDal(Context context) {
        this.context = context;
    }

    public void  CriarTabelaConta(){
        cn = new Conexao(context);
        cn.OpeneDb();;
        cn.CriaTabela(table,fieldId + " INTEGER," + fieldDescricao + " TEXT," + fieldDataCompra + " DATE," + fieldQuantidade + " INTEGER," + fieldEntrada + " REAL," + fieldValorParcela + " REAL," + fieldValor + " REAL," + fieldUsuario + " TEXT," + fieldStatus + " TEXT");
        cn.CloseDb();
    }

    public void DropTable(){
        cn = new Conexao(context);
        cn.OpeneDb();;
        cn.DropTable(table);
        cn.CloseDb();
    }

    public ArrayList<ContaDto> BuscaLista(ContaDto where){
        cn = new Conexao(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append(fieldId + ", ");
        sql.append(fieldDescricao + ", ");
        sql.append(fieldDataCompra + ", ");
        sql.append(fieldQuantidade + ", ");
        sql.append(fieldEntrada + ", ");
        sql.append(fieldValorParcela + ", ");
        sql.append(fieldValor + ", ");
        sql.append(fieldUsuario + ", ");
        sql.append(fieldStatus + ", ");
        sql.append("(SELECT COUNT(*) FROM " + tablePago + " AS P WHERE P.ID = A.ID AND P.STATUS = 'INATIVO') ");
        sql.append("FROM " + table + " AS A ");
        sql.append(" WHERE (SELECT COUNT(*) FROM " + tablePago + " AS P WHERE P.ID = A.ID AND P.STATUS = 'ATIVO') > 0 ");
        sql.append("ORDER BY " + fieldDataCompra + " ASC");


        ArrayList<ContaDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        contaDto = new ContaDto();
                        contaDto.Id =cursor.getLong(0);
                        contaDto.Descricao =cursor.getString(1);
                        contaDto.DataCompra = new Funcoes().ToDateSqLite(cursor.getString(2));

                        contaDto.QtdParcela =cursor.getInt(3);
                        contaDto.Entrada =cursor.getDouble(4);

                        if (cursor.getDouble(5) > 0){
                            contaDto.Parcelado = "Sim";
                        }
                        else{
                            contaDto.Parcelado = "Não";
                        }
                        contaDto.ValorParcela =cursor.getDouble(5);
                        contaDto.Valor =cursor.getDouble(6);
                        contaDto.Status =cursor.getString(8);
                        contaDto.ParcelaAtual =cursor.getInt(9) + 1;

                        lista.add(contaDto);
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

    public void Update(ContaDto obj){
        cn = new Conexao(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();
        cv.put(fieldDescricao,obj.Descricao);
        cv.put(fieldDataCompra, new Funcoes().DateSqliteToString(obj.DataCompra));
        cv.put(fieldQuantidade,obj.QtdParcela);
        cv.put(fieldEntrada,obj.Entrada);
        cv.put(fieldValorParcela,obj.ValorParcela);
        cv.put(fieldValor,obj.Valor);
        cv.put(fieldUsuario,"DANIEL.OLIVEIRA");
        cn.Update(table,cv,fieldId + " = " + obj.Id);
        cn.CloseDb();
    }

    public void Insert(ContaDto obj){
        cn = new Conexao(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();
        cv.put(fieldId,obj.Id);
        cv.put(fieldDescricao,obj.Descricao);
        cv.put(fieldDataCompra, new Funcoes().DateSqliteToString(obj.DataCompra));
        cv.put(fieldQuantidade,obj.QtdParcela);
        cv.put(fieldEntrada,obj.Entrada);
        cv.put(fieldValorParcela,obj.ValorParcela);
        cv.put(fieldValor,obj.Valor);
        cv.put(fieldUsuario,"DANIEL.OLIVEIRA");
        cv.put(fieldStatus,obj.Status);

        cn.Insert(table,cv);
        cn.CloseDb();
    }

    public Long VerificaSeExistePagamento(String id){
        cn = new Conexao(context);
        cn.OpeneDb();
        try{
            Cursor cursor = cn.BuscaLista("SELECT COUNT(*)  FROM " + tableLancamento + " WHERE IDCOMPRA = " + id);
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        return cursor.getLong(0);
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

        return Long.parseLong("0");
    }

    public Long ProximoCodigo(){
        cn = new Conexao(context);
        cn.OpeneDb();
        try{
            Cursor cursor = cn.BuscaLista("SELECT MAX(" + fieldId +  ")  FROM " + table);
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        return cursor.getLong(0) + 1;
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

        return  Long.parseLong("1");
    }

    public void DeletaLancamentosPago(String idCompra){
        cn = new Conexao(context);
        cn.OpeneDb();;
        cn.Delete(tablePago," IDCONTA = " + idCompra);
        cn.CloseDb();

    }

    public void DeletaLancamento(String idCompra){
        cn = new Conexao(context);
        cn.OpeneDb();;
        cn.Delete(tableLancamento," IDCOMPRA = " + idCompra);
        cn.CloseDb();

    }

    public void DeletaCompra(String idCompra){
        cn = new Conexao(context);
        cn.OpeneDb();;
        cn.Delete(table," ID = " + idCompra);
        cn.CloseDb();

    }
}
