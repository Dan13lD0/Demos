package RecyclerViews;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.app.devonaodevo.AcCadAltConta;
import com.android.app.devonaodevo.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import Controle.Funcoes;
import Dal.NovaCompraDal;
import Dto.ContaDto;

public class RecyclerNovaCompra extends RecyclerView.Adapter<RecyclerNovaCompra.ViewHolderListaConta>{
    private List<ContaDto> dados;
    private Context context;

    private DecimalFormat currency = new DecimalFormat("###,##0.00");

    public RecyclerNovaCompra(List<ContaDto> dados,Context context){
        this.dados = dados;
        this.context = context;
    }

    private List<ContaDto> ListaContas(Context context) {
        NovaCompraDal novaCompraDal = new NovaCompraDal(context);
        ArrayList<ContaDto> ListaContas = novaCompraDal.BuscaLista(new ContaDto());
        return ListaContas;
    }

    private void CarregaRecycler(Context context){

        RecyclerView rcContas = (RecyclerView) ((Activity)context).findViewById(R.id.rcNovaConta);
        ProgressBar progressBar = (ProgressBar) ((Activity)context).findViewById(R.id.pgLoading);
        LinearLayout linearLayout = (LinearLayout) ((Activity)context).findViewById(R.id.lnNotFound);
        TextView txtTotal = (TextView) ((Activity)context).findViewById(R.id.txtTotal);

        progressBar.setVisibility(View.VISIBLE);
        List<ContaDto> x = ListaContas(context);
        if (x.size() > 0){
            progressBar.setVisibility(View.GONE);
            rcContas.setVisibility(View.VISIBLE);
            new Funcoes().CarregaRecyclerViewSimple(rcContas,context,new RecyclerNovaCompra(x,context));
            Double total = 0.0;
            for (ContaDto o : x){
                total = total + o.Valor;
            }

            if (total > 0){
                txtTotal.setText(new Funcoes().RetornaValorString(new DecimalFormat("###,###,###,###,###.00").format(total)));
            }
            else {
                txtTotal.setText("0,00");
            }
        }
        else{
            progressBar.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            rcContas.setVisibility(View.GONE);
            txtTotal.setText("0,00");
        }


    }


    @NonNull
    @Override
    public RecyclerNovaCompra.ViewHolderListaConta onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.layout_grid_new_conta, viewGroup,false);
        RecyclerNovaCompra.ViewHolderListaConta holderSabores = new RecyclerNovaCompra.ViewHolderListaConta(view,viewGroup.getContext());
        return holderSabores;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerNovaCompra.ViewHolderListaConta viewHolder, final int i) {
        if ((dados != null) && (dados.size() > 0)) {
            final ContaDto conta = dados.get(i);
            viewHolder.txtDescricao1.setText(conta.Descricao);
            if (conta.ValorParcela > 0){
                viewHolder.txtDataCompra1.setText(new Funcoes().DateToString(conta.DataCompra));
                viewHolder.txtValor1.setText(String.valueOf(currency.format(conta.Valor)));
                viewHolder.txtQtde.setText(conta.ParcelaAtual.toString() + " / " + String.valueOf(conta.QtdParcela));
                viewHolder.txtValorParcela.setText(String.valueOf(currency.format(conta.ValorParcela)));
                viewHolder.txtEntrada.setText(String.valueOf(currency.format(conta.Entrada)));
            }
            else{
                viewHolder.txtTextoParcelado.setText("Data Compra");
                viewHolder.txtTextoValorParcela.setText("          Total");
                viewHolder.txtQtde.setText(new Funcoes().DateToString(conta.DataCompra));
                viewHolder.txtValorParcela.setText(String.valueOf(currency.format(conta.Valor)));
                viewHolder.txtTextoDataCompra.setVisibility(View.INVISIBLE);
                viewHolder.txtTextoTotal.setVisibility(View.INVISIBLE);
                viewHolder.txtDataCompra1.setVisibility(View.INVISIBLE);
                viewHolder.txtValor1.setVisibility(View.INVISIBLE);
                viewHolder.txtTextoEntrada.setVisibility(View.INVISIBLE);
                viewHolder.txtEntrada.setVisibility(View.INVISIBLE);
            }

            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
                    View bview = layoutInflater.inflate(R.layout.dialog_sair,null);

                    final Button btnSim = (Button) bview.findViewById(R.id.btnSim) ;
                    final Button btnNao = (Button) bview.findViewById(R.id.btnNao) ;
                    final TextView titulo = (TextView) bview.findViewById(R.id.txtTitle);
                    final TextView msg = (TextView) bview.findViewById(R.id.txtMsg);

                    titulo.setText("Apagar Registro");
                    msg.setText("Deseja apagar compra?");

                    builder.setView(bview);
                    final AlertDialog dialog = builder.create();

                    btnSim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new NovaCompraDal(v.getContext()).DeletaLancamento(conta.Id.toString());
                            new NovaCompraDal(v.getContext()).DeletaLancamentosPago(conta.Id.toString());
                            new NovaCompraDal(v.getContext()).DeletaCompra(conta.Id.toString());
                            CarregaRecycler(context);
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
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public  class ViewHolderListaConta extends  RecyclerView.ViewHolder{
        public TextView txtDescricao1;
        public TextView txtDataCompra1;
        public TextView txtValor1;
        public TextView txtQtde;
        public TextView txtValorParcela;
        public TextView txtTextoParcelado;
        public TextView txtTextoValorParcela;
        public TextView txtTextoDataCompra;
        public TextView txtTextoTotal;
        public TextView txtTextoEntrada;
        public TextView txtEntrada;
        public ImageView btnDelete;
        public int Position;

        public ViewHolderListaConta(@NonNull View itemView, final Context context) {
            super(itemView);
            txtDescricao1=itemView.findViewById(R.id.txtDescricao);
            txtDataCompra1=itemView.findViewById(R.id.txtTitulo);
            txtValor1=itemView.findViewById(R.id.txtValor);
            txtQtde=itemView.findViewById(R.id.txtQtde);
            txtValorParcela =itemView.findViewById(R.id.txtVlrParcela);
            txtTextoParcelado=itemView.findViewById(R.id.txtTextoParcelado);
            txtTextoValorParcela=itemView.findViewById(R.id.txtTextoParcela);
            txtTextoDataCompra=itemView.findViewById(R.id.txtTexoDataCompra);
            txtTextoTotal=itemView.findViewById(R.id.txtTextoTotal);
            btnDelete = itemView.findViewById(R.id.imgDelete);
            txtTextoEntrada=itemView.findViewById(R.id.txtTexoEntrada);
            txtEntrada=itemView.findViewById(R.id.txtEntrada);
            Position = getLayoutPosition();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dados.size() > 0){
                        ContaDto click = dados.get(getLayoutPosition());
                        if (new NovaCompraDal(v.getContext()).VerificaSeExistePagamento(click.Id.toString()) == 0){
                            Intent it = new Intent(context,AcCadAltConta.class);
                            it.putExtra("Alterar",click);
                            ((AppCompatActivity)context).startActivityForResult(it,0);
                        }
                        else{
                            new Funcoes().MessageBox(v,"Aviso","Já existem lançamentos referente a esta conta, e por esse motivo não podem ser alterados");
                        }

                    }
                }
            });
        }
    }
}
