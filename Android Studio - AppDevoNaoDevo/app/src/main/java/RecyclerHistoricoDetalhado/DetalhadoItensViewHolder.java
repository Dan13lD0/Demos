package RecyclerHistoricoDetalhado;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.devonaodevo.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Controle.Funcoes;
import Dal.ListaContasGeralDal;
import Dto.ListaGeralDetalhadaDto;

public class DetalhadoItensViewHolder extends ChildViewHolder {

    public TextView TxtDataCompra;
    public TextView TxtDescricao;
    public TextView  TxtParcelado;
    public TextView  TxtDataVencimento;
    public TextView  TxtDataPagamento;
    public TextView  TxtValorOriginal;
    public TextView  TxtValorPagamento;
    public TextView  TxtTotalPagamento;
    public TextView  TxtEntrada;
    public CardView cdData;
    public CardView cdTotal;
    public ImageView btnDelete;
    public RecyclerView rcLista;
    public TextView titulo;
    public Context mcontext;
    public ImageView ImgAcao;
    private DecimalFormat currency = new DecimalFormat("###,##0.00");

    public DetalhadoItensViewHolder(View itemView, Context context) {
        super(itemView);
        mcontext = context;
        TxtDataCompra      = (TextView) itemView.findViewById(R.id.txtTexoDataCompra);
        TxtDescricao       = (TextView)itemView.findViewById(R.id.txtDescricao);
        TxtParcelado       = (TextView)itemView.findViewById(R.id.txtParcelado);
        TxtDataVencimento  = (TextView)itemView.findViewById(R.id.txtDataVencimento);
        TxtDataPagamento   = (TextView)itemView.findViewById(R.id.txtDataPagamento);
        TxtValorOriginal   = (TextView)itemView.findViewById(R.id.txtValorOriginal);
        TxtValorPagamento  = (TextView)itemView.findViewById(R.id.txtValorPagamento);
        TxtTotalPagamento  = (TextView)itemView.findViewById(R.id.txtValorTotalpagamento);
        TxtEntrada         = (TextView)itemView.findViewById(R.id.txtValorEntrada);
        cdData = (CardView)itemView.findViewById(R.id.cdDate);
        cdTotal = (CardView)itemView.findViewById(R.id.cdTotal);
        btnDelete= (ImageView) itemView.findViewById(R.id.btnDelete);
        rcLista = (RecyclerView)((Activity)context).findViewById(R.id.rcDetalhes);
        titulo= (TextView)((Activity)context).findViewById(R.id.txtTitulo);

        ImgAcao= (ImageView) itemView.findViewById(R.id.imageView);
    }

    public void bind (final DetalhadoItens product){
        cdData.setVisibility(View.VISIBLE);
        cdTotal.setVisibility(View.VISIBLE);

        if (product.Index != "T"){
            if (product.Index != "I"){
                cdData.setVisibility(View.GONE);
            }

            if (product.Index != "F"){
                cdTotal.setVisibility(View.GONE);
            }
        }


        TxtDataCompra.setText(product.DataCompra);
        TxtDescricao.setText(product.Descricao);
        TxtParcelado.setText(product.Parcelado);
        TxtDataVencimento.setText(product.DataVencimento);

        Date dateNow = null;

        try {
            dateNow = new Funcoes().ToDate(new Funcoes().getDateNow());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date vencimento = null;

        try {
            vencimento = new Funcoes().ToDate(product.DataVencimento);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (product.DataPagamento.length() > 0){
            ImgAcao.setImageResource(R.drawable.ic_pago);
        }else if (dateNow.compareTo(vencimento) == 0){
            ImgAcao.setImageResource(R.drawable.ic_atencao);
        }
        else if (dateNow.compareTo(vencimento) > 0){
            ImgAcao.setImageResource(R.drawable.ic_vencidos);
        }
        else{
            ImgAcao.setImageResource(R.drawable.ic_normal);
        }

        btnDelete.setVisibility(View.VISIBLE);

        if (product.DataPagamento.length() > 0){
            TxtDataPagamento.setText(product.DataPagamento);
            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
                    View bview = layoutInflater.inflate(R.layout.dialog_sair,null);

                    final Button btnSim = (Button) bview.findViewById(R.id.btnSim) ;
                    final Button btnNao = (Button) bview.findViewById(R.id.btnNao) ;
                    final TextView titulo = (TextView) bview.findViewById(R.id.txtTitle);
                    final TextView msg = (TextView) bview.findViewById(R.id.txtMsg);

                    titulo.setText("Estornar");
                    msg.setText("Deseja Estornar pagamento?");

                    builder.setView(bview);
                    final AlertDialog dialog = builder.create();

                    btnSim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                         new ListaContasGeralDal(v.getContext()).DeletaLancamento(product.IdCompra,product.NroParcela);
                         new ListaContasGeralDal(v.getContext()).RetornaStatusPago(product.IdCompra,product.NroParcela);
                            CarregaRecycler(product.Param1,product.Param2,mcontext);
                            dialog.cancel();
                        }
                    });

                    btnNao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            });
        }
        else{
            TxtDataPagamento.setText("--//--");
            btnDelete.setVisibility(View.GONE);
        }
        TxtEntrada.setText(String.valueOf(currency.format(product.Entrada)));
        TxtValorOriginal.setText(String.valueOf(currency.format(product.ValorOriginal)));
        TxtValorPagamento.setText(String.valueOf(currency.format(product.ValorPagamento)));
        TxtTotalPagamento.setText(String.valueOf(currency.format(product.TotalPagamento)));
    }

    private void CarregaRecycler(String mesAbreviado,String mesExtenso,Context context) {
        titulo.setText(mesExtenso + " - " + mesAbreviado);

        String selectmes = "";

        if (mesAbreviado.contains("JANEIRO")){
            selectmes =mesAbreviado.replace("JANEIRO ","01/");
        }
        else if (mesAbreviado.contains("FEVEREIRO")){
            selectmes =mesAbreviado.replace("FEVEREIRO ","02/");
        }
        else if (mesAbreviado.contains("MARÇO")){
            selectmes =mesAbreviado.replace("MARÇO ","03/");
        }
        else if (mesAbreviado.contains("ABRIL")){
            selectmes =mesAbreviado.replace("ABRIL ","04/");
        }
        else if (mesAbreviado.contains("MAIO")){
            selectmes =mesAbreviado.replace("MAIO ","05/");
        }
        else if (mesAbreviado.contains("JUNHO")){
            selectmes =mesAbreviado.replace("JUNHO ","06/");
        }
        else if (mesAbreviado.contains("JULHO")){
            selectmes =mesAbreviado.replace("JULHO ","07/");
        }
        else if (mesAbreviado.contains("AGOSTO")){
            selectmes =mesAbreviado.replace("AGOSTO ","08/");
        }
        else if (mesAbreviado.contains("SETEMBRO")){
            selectmes =mesAbreviado.replace("SETEMBRO ","09/");
        }
        else if (mesAbreviado.contains("OUTUBRO")){
            selectmes =mesAbreviado.replace("OUTUBRO ","10/");
        }
        else if (mesAbreviado.contains("NOVEMBRO")){
            selectmes =mesAbreviado.replace("NOVEMBRO ","11/");
        }
        else{
            selectmes =mesAbreviado.replace("DEZEMBRO ","12/");
        }
        ArrayList<String> mes = new ListaContasGeralDal(context).ListaMesDetalhados(mesExtenso , selectmes);

        ArrayList<DetalhadoHeader> companies = new ArrayList<>();

        ArrayList<DetalhadoItens> itensGroup;
        ArrayList<ListaGeralDetalhadaDto> itens = null;
        for(String m : mes){
            String index = "I";
            Integer count = 1;
            itens = new ArrayList<>();
            itensGroup = new ArrayList<>();
            itens = new ListaContasGeralDal(context).DetalhadoContas(mesExtenso,m);
            for (ListaGeralDetalhadaDto a : itens){

                if(itens.size() == 1){
                    index = "T";
                }
                else{
                    if (itens.size() == count){
                        index = "F";
                    }
                }

                itensGroup.add(new DetalhadoItens(a.IdCompra,a.NroParcela,new Funcoes().DateToString(a.DataCompra),
                        a.Descricao,
                        a.Parcelado,
                        new Funcoes().DateToString(a.DataVencimento),
                        new Funcoes().DateToString(a.DataPagamento),
                        a.ValorOriginal,a.ValorPagamento,
                        a.TotalPagamento,a.Entrada,index,mesAbreviado,mesExtenso));
                count = count +1;

            }
            Date titulo = null;
            try {
                titulo = new Funcoes().ToDateSqLite(m);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            companies.add(new DetalhadoHeader(new Funcoes().DateToString(titulo),itensGroup));
        }

        DetalhadoItensAdapter adapter = new DetalhadoItensAdapter(companies,context);

        if (adapter.getItemCount() >0){
            rcLista.setAdapter(adapter);
        }
        else
        {
            ((Activity)context).finish();
        }


    }

}
