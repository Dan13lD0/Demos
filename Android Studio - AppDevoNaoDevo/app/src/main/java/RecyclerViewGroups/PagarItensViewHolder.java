package RecyclerViewGroups;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.devonaodevo.AcLancarPagar;
import com.android.app.devonaodevo.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Controle.Funcoes;
import Dto.ContaDto;

public class PagarItensViewHolder extends ChildViewHolder {
    public TextView idCompra;
    public TextView descricao;
    public TextView textParcela;
    public TextView parcela;
    public TextView textValor;
    public TextView textValor1;
    public TextView vlrPagar;
    public TextView vlrPagar2;
    public TextView textoVencimento;
    public TextView vencimento;

    public TextView totalParcelas;
    public TextView parcelaAtual;

    public CardView CdTotal;
    public CardView CdNovo;
    public TextView TextValorTotal;
    public ImageView ImgAcao;


    ContaDto click;

    private DecimalFormat currency = new DecimalFormat("###,##0.00");


    public PagarItensViewHolder(View itemView,final Context context) {
        super(itemView);

        idCompra = itemView.findViewById(R.id.txtIdCompra);
        CdTotal = itemView.findViewById(R.id.cdTotal);
        TextValorTotal  =itemView.findViewById(R.id.txtValorTotal);
        CdNovo = itemView.findViewById(R.id.cdNovaConta);
        descricao=itemView.findViewById(R.id.txtDescricao);
        textParcela =itemView.findViewById(R.id.txTextoParcelado);
        parcela=itemView.findViewById(R.id.txtParcelado);
        textValor =itemView.findViewById(R.id.txtTextoValor);
        textValor1 =itemView.findViewById(R.id.txtTextoValor1);
        vlrPagar=itemView.findViewById(R.id.txtValor);
        textoVencimento =itemView.findViewById(R.id.txtTextoData);
        vencimento =itemView.findViewById(R.id.txtData);
        vlrPagar2=itemView.findViewById(R.id.txtValor1);
        ImgAcao =itemView.findViewById(R.id.imageView);
        totalParcelas=itemView.findViewById(R.id.txtTotalParcelas);
        parcelaAtual=itemView.findViewById(R.id.txtParcelaAtual);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 click = new ContaDto();

                click.Id = Long.parseLong(idCompra.getText().toString());

                if (click.Id > 0){
                    click.Descricao = descricao.getText().toString();

                    if(parcela.getText().toString().length() > 7){
                        click.Valor = new Funcoes().RetornaValorDecimal(vlrPagar.getText().toString());
                    }
                    else{
                        click.Valor = new Funcoes().RetornaValorDecimal(vlrPagar2.getText().toString());
                    }

                    String parc = parcela.getText().toString().trim();
                    if (parc.length() != 10){
                        click.ParcelaAtual = Integer.parseInt(parcelaAtual.getText().toString()) ;
                        click.QtdParcela = Integer.parseInt(totalParcelas.getText().toString()) ;
                    }
                    else{
                        click.ParcelaAtual = 0 ;
                        click.QtdParcela = 0 ;
                    }

                    try {
                        click.DataCompra = new Funcoes().ToDate(vencimento.getText().toString());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }




                    Intent it = new Intent(context,AcLancarPagar.class);
                    it.putExtra("Alterar",click);
                    ((AppCompatActivity)context).startActivityForResult(it,0);
                }
            }
        });


    }



    public void bind (PagarItens product){

        idCompra.setText(product.id.toString());
        CdTotal.setVisibility(View.VISIBLE);
        CdNovo.setVisibility(View.VISIBLE);
        vlrPagar.setVisibility(View.VISIBLE);
        textValor.setVisibility(View.VISIBLE);
        textValor1.setVisibility(View.VISIBLE);
        vlrPagar2.setVisibility(View.VISIBLE);
        textoVencimento.setVisibility(View.VISIBLE);
        vencimento.setVisibility(View.VISIBLE);


        if (product.totalGeral > 0){
            TextValorTotal.setText(new Funcoes().RetornaValorString(new DecimalFormat("###,###,###,###,##0.00").format(product.totalGeral)));
            CdNovo.setVisibility(View.GONE);
        }
        else
        {
            CdNovo.setVisibility(View.VISIBLE);
            descricao.setText(product.descricao);

            if(product.qtdParcelas > 0){
               vlrPagar.setVisibility(View.GONE);
                vlrPagar2.setText(product.vlrPagar);

                totalParcelas.setText(String.valueOf(product.qtdParcelas));
                parcelaAtual.setText(String.valueOf(product.parcelaAtual));

                parcela.setText(String.valueOf(product.parcelaAtual) + "/" + String.valueOf(product.qtdParcelas));
                vencimento.setText(product.vencimento);
                textValor.setVisibility(View.GONE);
            }
            else {
                textParcela.setText("Vencimento");
                textValor1.setVisibility(View.GONE);
                vlrPagar.setText(product.total);
                vlrPagar2.setVisibility(View.GONE);
                parcela.setText(product.vencimento);
                textoVencimento.setVisibility(View.GONE);
                vencimento.setVisibility(View.GONE);
            }

            Date dateNow = null;

            try {
                dateNow = new Funcoes().ToDate(new Funcoes().getDateNow());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date vencimento = null;

            try {
                vencimento = new Funcoes().ToDate(product.vencimento);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (dateNow.compareTo(vencimento) == 0){
                ImgAcao.setImageResource(R.drawable.ic_atencao);
            }
            else if (dateNow.compareTo(vencimento) > 0){
                ImgAcao.setImageResource(R.drawable.ic_vencidos);
            }
            else{
                ImgAcao.setImageResource(R.drawable.ic_normal);
            }
            CdTotal.setVisibility(View.GONE);
        }

    }
}
