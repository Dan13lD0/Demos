package Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import Controle.Funcoes;
import Dto.GrupoMesDto;
import Dto.ListaGeralDetalhadaDto;

public class ListaContasGeralDal {

    private Context context;
    private Conexao cn;
    private GrupoMesDto obj;
    private ListaGeralDetalhadaDto objLista;
    private final String tablePago= "PAGO";
    private final String tableLancamento= "LANCAMENTO";

    public ListaContasGeralDal(Context context) {
        this.context = context;
    }

    public ArrayList<GrupoMesDto> BuscaListaMesPagos(){
        cn = new Conexao(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();

        sql.append("select \n" +
                " Case substr(p.datapagamento,6,2) \n" +
                " when '01' then 'Janeiro ' || substr(p.datapagamento,0,5) \n" +
                " when '02' then 'Fevereiro ' || substr(p.datapagamento,0,5) \n" +
                " when '03' then 'Março ' || substr(p.datapagamento,0,5) \n" +
                " when '04' then 'Abril ' || substr(p.datapagamento,0,5) \n" +
                " when '05' then 'Maio ' || substr(p.datapagamento,0,5) \n" +
                " when '06' then 'Junho ' || substr(p.datapagamento,0,5) \n" +
                " when '07' then 'Julho ' || substr(p.datapagamento,0,5) \n" +
                " when '08' then 'Agosto ' || substr(p.datapagamento,0,5) \n" +
                " when '09' then 'Setembro ' || substr(p.datapagamento,0,5) \n" +
                " when '10' then 'Outubro ' || substr(p.datapagamento,0,5) \n" +
                " when '11' then 'Novembro ' || substr(p.datapagamento,0,5) \n" +
                " else 'Dezembro ' || substr(p.datapagamento,0,5) end Extenso,\n" +
                "substr(p.datapagamento,6,2) || '/' || substr(p.datapagamento,0,5)  chave\n" +
                "from LANCAMENTO p \n" +
                "group by 1,2\n " +
                "order by substr(p.datapagamento,7,4),substr(p.datapagamento,4,2) asc  ");

        ArrayList<GrupoMesDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        obj = new GrupoMesDto();

                        obj.MesAbreviado = cursor.getString(cursor.getColumnIndex("chave"));
                        obj.MesExtenso = cursor.getString(cursor.getColumnIndex("Extenso"));
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

    public ArrayList<GrupoMesDto> BuscaListaMesVencidos(){
        cn = new Conexao(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();

        sql.append("select \n" +
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
                "and p.datavencimento < date('now') \n" +
                "group by 1,2\n" +
                "order by substr(p.datavencimento,7,4),substr(p.datavencimento,4,2) asc  ");

        ArrayList<GrupoMesDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        obj = new GrupoMesDto();

                        obj.MesAbreviado = cursor.getString(cursor.getColumnIndex("chave"));
                        obj.MesExtenso = cursor.getString(cursor.getColumnIndex("Extenso"));
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

    public ArrayList<GrupoMesDto> BuscaListaMesNaoPagos(){
        cn = new Conexao(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();

        sql.append("select \n" +
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
                //"order by substr(p.datavencimento,7,4),substr(p.datavencimento,4,2) asc  ");
                "order by p.datavencimento asc  ");

        ArrayList<GrupoMesDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        obj = new GrupoMesDto();

                        obj.MesAbreviado = cursor.getString(cursor.getColumnIndex("chave"));
                        obj.MesExtenso = cursor.getString(cursor.getColumnIndex("Extenso"));
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

    public ArrayList<GrupoMesDto> BuscaListaMesProximosVencimentos(){
        cn = new Conexao(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();
        Date now = null;
        try {
            now = new Funcoes().ToDate(new Funcoes().getDateNow());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sql.append("select \n" +
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
                "and p.datavencimento >= '" + new Funcoes().DateSqliteToString(now) + "' and p.datavencimento <= '" + new Funcoes().DateSqliteToString(new Funcoes().addMonth(now,1)) + "' \n" +
                "group by 1,2\n" +
                //"order by substr(p.datavencimento,7,4),substr(p.datavencimento,4,2) asc  ");
                "order by p.datavencimento asc  ");
        ArrayList<GrupoMesDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        obj = new GrupoMesDto();

                        obj.MesAbreviado = cursor.getString(cursor.getColumnIndex("chave"));
                        obj.MesExtenso = cursor.getString(cursor.getColumnIndex("Extenso"));
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

    public ArrayList<ListaGeralDetalhadaDto>DetalhadoContas(String tipo, String data){
        cn = new Conexao(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT C.ID,PARCELAATUAL, \n" +
                "strftime('%d/%m/%Y',C.DATACOMPRA) DATACOMPRA,\n" +
                "C.ID,\n" +
                "C.DESCRICAO,\n" +
                "\n" +
                "(CASE ifnull(C.QUANTIDADE,0) \n" +
                "WHEN 0 THEN \n" +
                "\t'À vista' \n" +
                "else\n" +
                "\tP.PARCELAATUAL || '/' || C.QUANTIDADE \n" +
                "end)\tPARCELADO,\n" +
                "strftime('%d/%m/%Y',P.DATAVENCIMENTO) DATAVENCIMENTO,\n" +
                "strftime('%d/%m/%Y',L.DATAPAGAMENTO) DATAPAGAMENTO,\n" +
                "P.VALOR VALORORIGINAL,\n" +
                "ifnull(L.VALORPAGAMENTO,0) VALORPAGAMENTO," +
                "ifnull(C.ENTRADA,0) ENTRADA,\n");
        if(tipo.trim().toUpperCase().equals("PAGOS")){
            sql.append(" ifnull((SELECT SUM(I.VALORPAGAMENTO) FROM LANCAMENTO I WHERE I.DATAPAGAMENTO = '" + data + "' and idcompra = c.id and p.parcelaatual = i.nroparcela),0)  TOTAL ");
        }
        else{
            sql.append(" 0  TOTAL ");
        }
        sql.append(" FROM CONTA C \n" +
                   "INNER JOIN PAGO P ON C.ID = P.IDCONTA \n" +
                   "LEFT JOIN LANCAMENTO L ON L.IDCOMPRA = C.ID ");


                //sql.append(" WHERE  strftime('d%/%m/%Y',p.datavencimento) =  strftime('d%/%m/%Y','" + data + "') ");
                sql.append(" WHERE  p.datavencimento = '" + data + "' ");
                if(tipo.trim().toUpperCase().equals("PAGOS")){
                    sql.append(" and exists(Select * from LANCAMENTO u where u.idcompra = p.idconta and p.parcelaatual = u.nroparcela) ");
                }

                if(tipo.trim().toUpperCase().equals("NÃO PAGOS")){
                    sql.append(" and not exists(Select * from LANCAMENTO u where u.idcompra = p.idconta and p.parcelaatual = u.nroparcela) ");
                }

                if(tipo.trim().toUpperCase().equals("PROXIMOS VENCIMENTOS")){
                    Date now = null;
                    try {
                        now = new Funcoes().ToDate(new Funcoes().getDateNow());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    sql.append(" AND P.STATUS = 'ATIVO' and p.datavencimento >= '" + new Funcoes().DateSqliteToString(now) + "' and p.datavencimento <= '" + new Funcoes().DateSqliteToString(new Funcoes().addMonth(now,1)) + "' ");


                }

                if(tipo.trim().toUpperCase().equals("VENCIDOS")){
                    Date now = null;
                    try {
                        now = new Funcoes().ToDate(new Funcoes().getDateNow());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    sql.append(" AND P.STATUS = 'ATIVO' and p.datavencimento < '" + new Funcoes().DateSqliteToString(now) + "' ");
                }
                sql.append(" ORDER BY C.DATACOMPRA,ifnull(C.QUANTIDADE,0) ASC ");

        ArrayList<ListaGeralDetalhadaDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        objLista = new ListaGeralDetalhadaDto();
                        objLista.IdCompra =  cursor.getString(cursor.getColumnIndex("ID"));
                        objLista.NroParcela =  cursor.getString(cursor.getColumnIndex("PARCELAATUAL"));
                        objLista.DataCompra = new Funcoes().ToDate(cursor.getString(cursor.getColumnIndex("DATACOMPRA")));
                        objLista.Descricao = cursor.getString(cursor.getColumnIndex("DESCRICAO"));
                        objLista.Parcelado = cursor.getString(cursor.getColumnIndex("PARCELADO"));
                        objLista.DataVencimento = new Funcoes().ToDate(cursor.getString(cursor.getColumnIndex("DATAVENCIMENTO")));

                        if (cursor.getString(cursor.getColumnIndex("DATAPAGAMENTO")) != null){
                            objLista.DataPagamento = new Funcoes().ToDate(cursor.getString(cursor.getColumnIndex("DATAPAGAMENTO")));
                        }

                        objLista.Entrada = new Funcoes().RetornaValorDecimal(cursor.getString(cursor.getColumnIndex("ENTRADA")));
                        objLista.ValorOriginal = new Funcoes().RetornaValorDecimal(cursor.getString(cursor.getColumnIndex("VALORORIGINAL")));
                        objLista.ValorPagamento = new Funcoes().RetornaValorDecimal(cursor.getString(cursor.getColumnIndex("VALORPAGAMENTO")));
                        objLista.TotalPagamento = new Funcoes().RetornaValorDecimal(cursor.getString(cursor.getColumnIndex("TOTAL")));
                        lista.add(objLista);
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

    public ArrayList<ListaGeralDetalhadaDto>DetalhadoContasPagos(String tipo, String data){
        cn = new Conexao(context);
        cn.OpeneDb();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT C.ID,PARCELAATUAL, \n" +
                "strftime('%d/%m/%Y',C.DATACOMPRA) DATACOMPRA,\n" +
                "C.ID,\n" +
                "C.DESCRICAO,\n" +
                "\n" +
                "(CASE ifnull(C.QUANTIDADE,0) \n" +
                "WHEN 0 THEN \n" +
                "\t'À vista' \n" +
                "else\n" +
                "\tP.PARCELAATUAL || '/' || C.QUANTIDADE \n" +
                "end)\tPARCELADO,\n" +
                "strftime('%d/%m/%Y',P.DATAVENCIMENTO) DATAVENCIMENTO,\n" +
                "strftime('%d/%m/%Y',L.DATAPAGAMENTO) DATAPAGAMENTO,\n" +
                "P.VALOR VALORORIGINAL,\n" +
                "ifnull(L.VALORPAGAMENTO,0) VALORPAGAMENTO," +
                "ifnull(C.ENTRADA,0) ENTRADA,\n");
        sql.append(" ifnull((SELECT SUM(I.VALORPAGAMENTO) FROM LANCAMENTO I WHERE I.DATAPAGAMENTO = '" + data + "' ),0)  TOTAL ");
        sql.append(" FROM CONTA C \n" +
                "INNER JOIN PAGO P ON C.ID = P.IDCONTA \n" +
                "LEFT JOIN LANCAMENTO L ON L.IDCOMPRA = C.ID ");



        //where strftime('%m/%Y',l.datapagamento) = '01/2019'
        sql.append(" WHERE  l.datapagamento = '" + data + "' ");

        sql.append(" ORDER BY C.DATACOMPRA,ifnull(C.QUANTIDADE,0) ASC ");

        ArrayList<ListaGeralDetalhadaDto>lista = new ArrayList<>();
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        objLista = new ListaGeralDetalhadaDto();
                        objLista.IdCompra =  cursor.getString(cursor.getColumnIndex("ID"));
                        objLista.NroParcela =  cursor.getString(cursor.getColumnIndex("PARCELAATUAL"));
                        objLista.DataCompra = new Funcoes().ToDate(cursor.getString(cursor.getColumnIndex("DATACOMPRA")));
                        objLista.Descricao = cursor.getString(cursor.getColumnIndex("DESCRICAO"));
                        objLista.Parcelado = cursor.getString(cursor.getColumnIndex("PARCELADO"));
                        objLista.DataVencimento = new Funcoes().ToDate(cursor.getString(cursor.getColumnIndex("DATAVENCIMENTO")));

                        if (cursor.getString(cursor.getColumnIndex("DATAPAGAMENTO")) != null){
                            objLista.DataPagamento = new Funcoes().ToDate(cursor.getString(cursor.getColumnIndex("DATAPAGAMENTO")));
                        }

                        objLista.Entrada = new Funcoes().RetornaValorDecimal(cursor.getString(cursor.getColumnIndex("ENTRADA")));
                        objLista.ValorOriginal = new Funcoes().RetornaValorDecimal(cursor.getString(cursor.getColumnIndex("VALORORIGINAL")));
                        objLista.ValorPagamento = new Funcoes().RetornaValorDecimal(cursor.getString(cursor.getColumnIndex("VALORPAGAMENTO")));
                        objLista.TotalPagamento = new Funcoes().RetornaValorDecimal(cursor.getString(cursor.getColumnIndex("TOTAL")));
                        lista.add(objLista);
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

    public ArrayList<String> ListaMesPagos(String tipo,String data){
        cn = new Conexao(context);
        cn.OpeneDb();
        ArrayList<String>Lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("Select l.datapagamento from LANCAMENTO l where strftime('%m/%Y',l.datapagamento) = '" + data +  "' group by l.datapagamento order by l.datapagamento asc");

        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        String x = cursor.getString(0);
                        Lista.add(x);
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
        return  Lista;
    }

    public ArrayList<String> ListaMesDetalhados(String tipo,String data){
        cn = new Conexao(context);
        cn.OpeneDb();
        ArrayList<String>Lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("select p.datavencimento from PAGO p ");

        if(tipo.trim().toUpperCase().equals("PAGOS")){
            sql.append(" WHERE exists(Select * from LANCAMENTO u where u.idcompra = p.idconta and p.parcelaatual = u.nroparcela) ");
        }

        if(tipo.trim().toUpperCase().equals("NÃO PAGOS")){
            sql.append(" WHERE not exists(Select * from LANCAMENTO u where u.idcompra = p.idconta and p.parcelaatual = u.nroparcela) ");
        }

        if(tipo.trim().toUpperCase().equals("PROXIMOS VENCIMENTOS")){
            Date now = null;
            try {
                now = new Funcoes().ToDate(new Funcoes().getDateNow());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sql.append(" WHERE P.STATUS = 'ATIVO' and p.datavencimento >= '" + new Funcoes().DateSqliteToString(now) + "' and p.datavencimento <= '" + new Funcoes().DateSqliteToString(new Funcoes().addMonth(now,1)) + "' ");


        }

        if(tipo.trim().toUpperCase().equals("VENCIDOS")){
            Date now = null;
            try {
                now = new Funcoes().ToDate(new Funcoes().getDateNow());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sql.append(" WHERE P.STATUS = 'ATIVO' and p.datavencimento < '" + new Funcoes().DateSqliteToString(now) + "' ");
        }

        sql.append(" AND (substr(p.datavencimento,6,2) || '/' || substr(p.datavencimento,0,5) ) = '" + data + "' ");
        //sql.append(" group by p.datavencimento order by substr(p.datavencimento,7,4),substr(p.datavencimento,4,2) asc  ");
        sql.append(" group by p.datavencimento order by p.datavencimento asc  ");
        try{
            Cursor cursor = cn.BuscaLista(sql.toString());
            if(cursor.getCount() >0){
                if (cursor.moveToFirst()){
                    do{
                        String x = cursor.getString(0);
                        Lista.add(x);
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
        return  Lista;
    }

    public void DeletaLancamento(String idCompra,String nroParcela){
        cn = new Conexao(context);
        cn.OpeneDb();;
        cn.Delete(tableLancamento," IDCOMPRA = " + idCompra + " AND NROPARCELA = " + nroParcela);
        cn.CloseDb();

    }

    public void RetornaStatusPago(String idCompra,String nroParcela){
        cn = new Conexao(context);
        cn.OpeneDb();
        ContentValues cv = new ContentValues();
        cv.put("STATUS","ATIVO");
        cn.Update(tablePago,cv," IDCONTA = " + idCompra + " AND PARCELAATUAL = " + nroParcela);
        cn.CloseDb();

    }
}
