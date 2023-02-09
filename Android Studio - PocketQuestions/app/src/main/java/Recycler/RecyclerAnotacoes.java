package Recycler;

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
import android.widget.TextView;

import com.android.pocketquestions.AcCadAltAnotacao;
import com.android.pocketquestions.R;

import java.util.List;

import Dto.AnotacaoDto;
import Funcoes.Funcoes;

public class RecyclerAnotacoes extends RecyclerView.Adapter<RecyclerAnotacoes.RecyclerViewHolderAnotacoes> {

    private List<AnotacaoDto> dados;
    private Context context;

    public RecyclerAnotacoes(List<AnotacaoDto> dados, Context context) {
        this.dados = dados;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAnotacoes.RecyclerViewHolderAnotacoes onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.layout_recycler_anotacoes, viewGroup,false);
        RecyclerAnotacoes.RecyclerViewHolderAnotacoes holderCategoria = new RecyclerAnotacoes.RecyclerViewHolderAnotacoes(view,context);
        return holderCategoria;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAnotacoes.RecyclerViewHolderAnotacoes viewHolder, int i) {
        if ((dados != null) && (dados.size() > 0)) {
            AnotacaoDto obj = dados.get(i);
            viewHolder.Titulo.setText(obj.Titulo);
        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class RecyclerViewHolderAnotacoes extends RecyclerView.ViewHolder{
        public TextView Titulo;
        public ImageView imgDelete;

        public RecyclerViewHolderAnotacoes(@NonNull View itemView,final Context context) {
            super(itemView);
            Titulo = (TextView)itemView.findViewById(R.id.txtTitulo);
            imgDelete = (ImageView)itemView.findViewById(R.id.imgDelete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageFinaliza("Deletar","Deseja deletar este registro!",v.getContext());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dados.size()>0){
                        Intent it = new Intent(context, AcCadAltAnotacao.class);
                        AnotacaoDto per = dados.get(getLayoutPosition());
                        it.putExtra("Alterar",per);
                        ((AppCompatActivity)context).startActivityForResult(it,0);
                    }
                }
            });
        }

        private void MessageFinaliza(String titulo,String msg,Context context){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.dialog_message_question,null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            final Button btnSim = (Button) view.findViewById(R.id.btnSim) ;
            final Button btnNao = (Button) view.findViewById(R.id.btnNao) ;

            TextView txtTitulo = view.findViewById(R.id.txtTitulo);
            TextView txtDescricao = view.findViewById(R.id.txtDescricao);
            txtTitulo.setText(titulo);
            txtDescricao.setText(msg);

            builder.setView(view);
            final AlertDialog dialog = builder.create();

            btnSim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
    }
}
