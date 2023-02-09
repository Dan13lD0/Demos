package Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import Controle.Funcoes;
import Dto.ContaDto;
import Dto.GrupoMesDto;
import Dto.PagoDto;

public class PagoDal {

    private final String tablePago = "PAGO";
    private final String fieldPagoId = "ID";
    private final String fieldPagoIdConta = "IDCONTA";
    private final String fieldPagoDataVencimento = "DATAVENCIMENTO";
    private final String fieldPagoValor = "VALOR";
    private final String fieldPagoNumeroParcela = "PARCELAATUAL";
    private final String fieldPagoStatus = "STATUS";

    private final String tableConta = "CONTA";
    private final String fieldContaId = "ID";
    private final String fieldContaDescricao = "DESCRICAO";
    private final String fieldContaDataCompra = "DATACOMPRA";
    private final String fieldContaQuantidade = "QUANTIDADE";
    private final String fieldContaEntrada = "ENTRADA";
    private final String fieldContaValorParcela = "VALORPARCELA";
    private final String fieldContaValor = "VALOR";
    private final String fieldContaUsuario = "USUARIO";
    private final String fieldContaStatus = "STATUS";

    private Context context;
    private Conexao cn;

    private ContaDto contaDto;
    private GrupoMesDto grupoDto;

    public PagoDal(Context context) {
        this.context = context;
    }

    public void  CriarTabelaConta(){
        cn = new Conexao(context);
        cn.OpeneDb();;
        cn.CriaTabela(tablePago,fieldPagoId + " INTEGER, "  + fieldPagoIdConta + " INTEGER," + fieldPagoDataVencimento + " DATE," + fieldPagoValor + " REAL, " + fieldPagoNumeroParcela + " INTEGER, " + fieldPagoStatus + " TEXT");
        cn.CloseDb();
    }

    public Long ProximoCodigo(){
        cn = new Conexao(context);
        cn.OpeneDb();
        try{
            Cursor cursor = cn.BuscaLista("SELECT MAX(" + fieldPagoId +  ")  FROM " + tablePago);
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

    public void Insert(PagoDto obj){
        cn = new Conexao(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();
        cv.put(fieldPagoId,obj.PagoId);
        cv.put(fieldPagoIdConta,obj.PagoIdConta);
        cv.put(fieldPagoDataVencimento, new Funcoes().DateSqliteToString(obj.PagoDataVencimento));
        cv.put(fieldPagoValor,obj.PagoValor);
        cv.put(fieldPagoNumeroParcela,obj.PagoNumeroParcela);
        cv.put(fieldPagoStatus,obj.PagoStatus);

        try{
            cn.Insert(tablePago,cv);
        }
        catch(Exception t){
            String we = t.getMessage();

        }
        cn.CloseDb();
    }

    public void DropTable(){
        cn = new Conexao(context);
        cn.OpeneDb();;
        cn.DropTable(tablePago);
        cn.CloseDb();
    }

    public ArrayList<ContaDto> BuscaLista(String data){
        cn = new Conexao(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT \n" +
                "C.ID,\n" +
                "C.DESCRICAO,\n" +
                "P.DATAVENCIMENTO,\n" +
                "C.QUANTIDADE,\n" +
                "C.ENTRADA,\n" +
                "CASE WHEN C.QUANTIDADE > 0 THEN \n" +
                "'Sim'\n" +
                "else\n" +
                "'Não'\n" +
                "end Parcelado,\n" +
                "C.VALORPARCELA,\n" +
                "C.VALOR,\n" +
                "C.STATUS,\n" +
                "P.PARCELAATUAL\n" +
                "FROM CONTA C JOIN PAGO P ON C.ID = P.IDCONTA\n" +
                "WHERE (substr(P.DATAVENCIMENTO,6,2) || '/' || substr(P.DATAVENCIMENTO,0,5)) = '" + data + "'\n" +
                "AND P.STATUS = 'ATIVO'");


        ArrayList<ContaDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        contaDto = new ContaDto();
                        contaDto.Id =cursor.getLong(cursor.getColumnIndex("ID"));
                        contaDto.Descricao =cursor.getString(cursor.getColumnIndex("DESCRICAO"));
                        contaDto.DataCompra = new Funcoes().ToDateSqLite(cursor.getString(cursor.getColumnIndex("DATAVENCIMENTO")));

                        contaDto.QtdParcela =cursor.getInt(cursor.getColumnIndex("QUANTIDADE"));
                        contaDto.Entrada =cursor.getDouble(cursor.getColumnIndex("ENTRADA"));
                        contaDto.Parcelado = cursor.getString(cursor.getColumnIndex("Parcelado"));

                        contaDto.ValorParcela =cursor.getDouble(cursor.getColumnIndex("VALORPARCELA"));
                        contaDto.Valor =cursor.getDouble(cursor.getColumnIndex("VALOR"));
                        contaDto.Status =cursor.getString(cursor.getColumnIndex("STATUS"));
                        contaDto.ParcelaAtual = cursor.getInt(cursor.getColumnIndex("PARCELAATUAL")) ;
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


    public ArrayList<GrupoMesDto> BuscaListaGrupo(){
        cn = new Conexao(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();

        sql.append("select\n" +
                "substr(p.datavencimento,6,2) , \n" +
                " Case substr(p.datavencimento,6,2) \n" +
                " when '01' then 'Janeiro ' || substr(p.datavencimento,0,5) \n" +
                " when '02' then 'Fevereiro ' || substr(p.datavencimento,0,5) \n" +
                " when '03' then 'Março ' || substr(p.datavencimento,0,5) \n" +
                " when '04' then 'Abril ' || substr(p.datavencimento,0,5) \n" +
                " when '05' then 'Maio ' || substr(p.datavencimento,0,5) \n" +
                " when '06' then 'Junho ' || substr(p.datavencimento,0,5) \n" +
                " when '07' then 'Julho ' || substr(p.datavencimento,0,5) \n" +
                " when '08' then 'Agosto ' || substr(p.datavencimento,0,5) \n" +
                " when '09' then 'Setembro ' || substr(p.datavencimento,0,5) \n" +
                " when '10' then 'Outubro ' || substr(p.datavencimento,0,5) \n" +
                " when '11' then 'Novembro ' || substr(p.datavencimento,0,5) \n" +
                " else 'Dezembro ' || substr(p.datavencimento,0,5) end Extenso,\n" +
                "substr(p.datavencimento,6,2) || '/' || substr(p.datavencimento,0,5)  chave\n" +
                "from PAGO p \n" +
                "WHERE P.STATUS = 'ATIVO'\n" +
                "group by 1,2\n" +
                "order by p.datavencimento asc  ");

        ArrayList<GrupoMesDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        grupoDto = new GrupoMesDto();
                        grupoDto.MesExtenso = cursor.getString(cursor.getColumnIndex("Extenso"));
                        grupoDto.MesAbreviado = cursor.getString(cursor.getColumnIndex("chave"));
                        lista.add(grupoDto);
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

    public ArrayList<GrupoMesDto> BuscaListaVencer(){
        cn = new Conexao(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();
        Date now = null;
        try {
            now = new Funcoes().ToDate(new Funcoes().getDateNow());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sql.append("select\n" +
                "substr(p.datavencimento,6,2) , \n" +
                " Case substr(p.datavencimento,6,2) \n" +
                " when '01' then 'Janeiro ' || substr(p.datavencimento,0,5) \n" +
                " when '02' then 'Fevereiro ' || substr(p.datavencimento,0,5) \n" +
                " when '03' then 'Março ' || substr(p.datavencimento,0,5) \n" +
                " when '04' then 'Abril ' || substr(p.datavencimento,0,5) \n" +
                " when '05' then 'Maio ' || substr(p.datavencimento,0,5) \n" +
                " when '06' then 'Junho ' || substr(p.datavencimento,0,5) \n" +
                " when '07' then 'Julho ' || substr(p.datavencimento,0,5) \n" +
                " when '08' then 'Agosto ' || substr(p.datavencimento,0,5) \n" +
                " when '09' then 'Setembro ' || substr(p.datavencimento,0,5) \n" +
                " when '10' then 'Outubro ' || substr(p.datavencimento,0,5) \n" +
                " when '11' then 'Novembro ' || substr(p.datavencimento,0,5) \n" +
                " else 'Dezembro ' || substr(p.datavencimento,0,5) end Extenso,\n" +
                "substr(p.datavencimento,6,2) || '/' || substr(p.datavencimento,0,5)  chave\n" +
                "from PAGO p \n" +
                "WHERE P.STATUS = 'ATIVO'  \n" +
                " and p.datavencimento >= '" + new Funcoes().DateSqliteToString(now) + "' and p.datavencimento <= '" + new Funcoes().DateSqliteToString(new Funcoes().addMonth(now,1)) + "' \n" +
                "group by 1,2\n" +
                "order by p.datavencimento asc  ");

        ArrayList<GrupoMesDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        grupoDto = new GrupoMesDto();
                        grupoDto.MesExtenso = cursor.getString(cursor.getColumnIndex("Extenso"));
                        grupoDto.MesAbreviado = cursor.getString(cursor.getColumnIndex("chave"));
                        lista.add(grupoDto);
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

    public ArrayList<ContaDto> BuscaListaVencerItens(String data){
        cn = new Conexao(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();
        Date now = null;
        try {
            now = new Funcoes().ToDate(new Funcoes().getDateNow());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sql.append("SELECT \n" +
                "C.ID,\n" +
                "C.DESCRICAO,\n" +
                "P.DATAVENCIMENTO,\n" +
                "C.QUANTIDADE,\n" +
                "C.ENTRADA,\n" +
                "CASE WHEN C.QUANTIDADE > 0 THEN \n" +
                "'Sim'\n" +
                "else\n" +
                "'Não'\n" +
                "end Parcelado,\n" +
                "C.VALORPARCELA,\n" +
                "C.VALOR,\n" +
                "C.STATUS,\n" +
                "P.PARCELAATUAL\n" +
                "FROM CONTA C JOIN PAGO P ON C.ID = P.IDCONTA\n" +
                "WHERE (substr(P.DATAVENCIMENTO,6,2) || '/' || substr(P.DATAVENCIMENTO,0,5)) = '" + data + "'   \n" +
                " and p.datavencimento >= '" + new Funcoes().DateSqliteToString(now) + "' and p.datavencimento <= '" + new Funcoes().DateSqliteToString(new Funcoes().addMonth(now,1)) + "' \n" +
                "AND P.STATUS = 'ATIVO'");


        ArrayList<ContaDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        contaDto = new ContaDto();
                        contaDto.Id =cursor.getLong(cursor.getColumnIndex("ID"));
                        contaDto.Descricao =cursor.getString(cursor.getColumnIndex("DESCRICAO"));
                        contaDto.DataCompra = new Funcoes().ToDateSqLite(cursor.getString(cursor.getColumnIndex("DATAVENCIMENTO")));

                        contaDto.QtdParcela =cursor.getInt(cursor.getColumnIndex("QUANTIDADE"));
                        contaDto.Entrada =cursor.getDouble(cursor.getColumnIndex("ENTRADA"));
                        contaDto.Parcelado = cursor.getString(cursor.getColumnIndex("Parcelado"));

                        contaDto.ValorParcela =cursor.getDouble(cursor.getColumnIndex("VALORPARCELA"));
                        contaDto.Valor =cursor.getDouble(cursor.getColumnIndex("VALOR"));
                        contaDto.Status =cursor.getString(cursor.getColumnIndex("STATUS"));
                        contaDto.ParcelaAtual = cursor.getInt(cursor.getColumnIndex("PARCELAATUAL")) ;
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

    public ArrayList<GrupoMesDto> BuscaListaVencidos(){
        cn = new Conexao(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();

        sql.append("select\n" +
                "substr(p.datavencimento,6,2) , \n" +
                " Case substr(p.datavencimento,6,2) \n" +
                " when '01' then 'Janeiro ' || substr(p.datavencimento,0,5) \n" +
                " when '02' then 'Fevereiro ' || substr(p.datavencimento,0,5) \n" +
                " when '03' then 'Março ' || substr(p.datavencimento,0,5) \n" +
                " when '04' then 'Abril ' || substr(p.datavencimento,0,5) \n" +
                " when '05' then 'Maio ' || substr(p.datavencimento,0,5) \n" +
                " when '06' then 'Junho ' || substr(p.datavencimento,0,5) \n" +
                " when '07' then 'Julho ' || substr(p.datavencimento,0,5) \n" +
                " when '08' then 'Agosto ' || substr(p.datavencimento,0,5) \n" +
                " when '09' then 'Setembro ' || substr(p.datavencimento,0,5) \n" +
                " when '10' then 'Outubro ' || substr(p.datavencimento,0,5) \n" +
                " when '11' then 'Novembro ' || substr(p.datavencimento,0,5) \n" +
                " else 'Dezembro ' || substr(p.datavencimento,0,5) end Extenso,\n" +
                "substr(p.datavencimento,6,2) || '/' || substr(p.datavencimento,0,5)  chave\n" +
                "from PAGO p \n" +
                "WHERE P.STATUS = 'ATIVO' AND p.datavencimento < date('now') \n" +
                "group by 1,2\n" +
                "order by p.datavencimento asc  ");

        ArrayList<GrupoMesDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        grupoDto = new GrupoMesDto();
                        grupoDto.MesExtenso = cursor.getString(cursor.getColumnIndex("Extenso"));
                        grupoDto.MesAbreviado = cursor.getString(cursor.getColumnIndex("chave"));
                        lista.add(grupoDto);
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

    public ArrayList<ContaDto> BuscaListaVencidosItens(String data){
        cn = new Conexao(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT \n" +
                "C.ID,\n" +
                "C.DESCRICAO,\n" +
                "P.DATAVENCIMENTO,\n" +
                "C.QUANTIDADE,\n" +
                "C.ENTRADA,\n" +
                "CASE WHEN C.QUANTIDADE > 0 THEN \n" +
                "'Sim'\n" +
                "else\n" +
                "'Não'\n" +
                "end Parcelado,\n" +
                "C.VALORPARCELA,\n" +
                "C.VALOR,\n" +
                "C.STATUS,\n" +
                "P.PARCELAATUAL\n" +
                "FROM CONTA C JOIN PAGO P ON C.ID = P.IDCONTA\n" +
                "WHERE (substr(P.DATAVENCIMENTO,6,2) || '/' || substr(P.DATAVENCIMENTO,0,5)) = '" + data + "' AND p.datavencimento < date('now')  \n" +
                "AND P.STATUS = 'ATIVO'");


        ArrayList<ContaDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        contaDto = new ContaDto();
                        contaDto.Id =cursor.getLong(cursor.getColumnIndex("ID"));
                        contaDto.Descricao =cursor.getString(cursor.getColumnIndex("DESCRICAO"));
                        contaDto.DataCompra = new Funcoes().ToDateSqLite(cursor.getString(cursor.getColumnIndex("DATAVENCIMENTO")));

                        contaDto.QtdParcela =cursor.getInt(cursor.getColumnIndex("QUANTIDADE"));
                        contaDto.Entrada =cursor.getDouble(cursor.getColumnIndex("ENTRADA"));
                        contaDto.Parcelado = cursor.getString(cursor.getColumnIndex("Parcelado"));

                        contaDto.ValorParcela =cursor.getDouble(cursor.getColumnIndex("VALORPARCELA"));
                        contaDto.Valor =cursor.getDouble(cursor.getColumnIndex("VALOR"));
                        contaDto.Status =cursor.getString(cursor.getColumnIndex("STATUS"));
                        contaDto.ParcelaAtual = cursor.getInt(cursor.getColumnIndex("PARCELAATUAL")) ;
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
}
