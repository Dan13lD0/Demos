package Recycler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pocketquestions.AcCadAltCategoria;
import com.android.pocketquestions.FragmentCategoria;
import com.android.pocketquestions.R;

import java.util.List;

import Dal.CategoriaDal;
import Dto.CategoriaDto;

public class RecyclerCategoria extends RecyclerView.Adapter<RecyclerCategoria.RecyclerViewHolderCategoria> {

    private List<CategoriaDto> dados;
    private Context context;

    public RecyclerCategoria(List<CategoriaDto> dados,Context context) {
        this.dados = dados;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerCategoria.RecyclerViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.layout_recycler_categoria, viewGroup,false);
        RecyclerCategoria.RecyclerViewHolderCategoria holderCategoria = new RecyclerCategoria.RecyclerViewHolderCategoria(view,context);
        return holderCategoria;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerCategoria.RecyclerViewHolderCategoria viewHolder, int i) {
        if ((dados != null) && (dados.size() > 0)) {
            CategoriaDto obj = dados.get(i);
            viewHolder.Codigo.setText(obj.Id.toString());
            viewHolder.Descricao.setText(obj.Descricao);
            if (obj.Status.equals("0")){
                viewHolder.Status.setText("Ativo");
            }
            else{
                viewHolder.Status.setText("Inativo");
            }

        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class RecyclerViewHolderCategoria extends RecyclerView.ViewHolder{
        public TextView Codigo;
        public TextView Descricao;
        public TextView Status;
        public ImageView imgDelete;

        public RecyclerViewHolderCategoria(@NonNull View itemView,final Context context) {
            super(itemView);
            Codigo = (TextView)itemView.findViewById(R.id.txtCodigo);
            Descricao = (TextView)itemView.findViewById(R.id.txtDescricao);
            Status = (TextView)itemView.findViewById(R.id.txtStatus);
            imgDelete = (ImageView)itemView.findViewById(R.id.imgDelete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dados.size()>0){
                        CategoriaDto cat = dados.get(getLayoutPosition());
                        if (new CategoriaDal(v.getContext()).RetornaPerguntas(cat.Descricao) == false){
                            MessageFinaliza("Deletar","Deseja deletar este registro!",v.getContext(),cat.Descricao);
                        }
                        else
                        {
                            MessageFinaliza("Deletar","Deseja deletar este registro, lembrando que neste registro existem perguntas vinculas que seram apagadas!",v.getContext(),cat.Descricao);
                        }

                    }

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dados.size()>0){
                        Intent it = new Intent(context, AcCadAltCategoria.class);
                        CategoriaDto cat = dados.get(getLayoutPosition());
                        it.putExtra("Alterar",cat);

                        ((AppCompatActivity)context).startActivityForResult(it,0);
                    }
                }
            });
        }

        private void MessageFinaliza(String titulo, String msg, final Context context, final String categoria){
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
                    new CategoriaDal(v.getContext()).DeletaCategoria(categoria);
                    new CategoriaDal(v.getContext()).DeletaListaPergunta(categoria);
                    RecyclerView recyclerView = ((AppCompatActivity)context).findViewById(R.id.rcCategoria);
                    recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                    RecyclerCategoria lista = new RecyclerCategoria(new CategoriaDal(v.getContext()).RetornaCategoria(),v.getContext());
                    recyclerView.setAdapter(lista);
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
