package Recycler;

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
import com.android.pocketquestions.AcCadAltPergunta;
import com.android.pocketquestions.R;

import java.util.ArrayList;
import java.util.List;

import Dal.PerguntaDal;
import Dto.CategoriaDto;
import Dto.PerguntaDto;

public class RecyclerPergunta extends RecyclerView.Adapter<RecyclerPergunta.RecyclerViewHolderPergunta> {

    private List<PerguntaDto> dados;
    private Context context;

    public RecyclerPergunta(List<PerguntaDto> dados, Context context) {
        this.dados = dados;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerPergunta.RecyclerViewHolderPergunta onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.layout_recycler_pergunta, viewGroup,false);
        RecyclerPergunta.RecyclerViewHolderPergunta holderCategoria = new RecyclerPergunta.RecyclerViewHolderPergunta(view,context);
        return holderCategoria;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerPergunta.RecyclerViewHolderPergunta viewHolder, int i) {
        if ((dados != null) && (dados.size() > 0)) {
            PerguntaDto obj = dados.get(i);
            viewHolder.Titulo.setText(obj.Categoria);
            viewHolder.Descricao.setText(obj.Pergunta);
        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class RecyclerViewHolderPergunta extends RecyclerView.ViewHolder{
        public TextView Titulo;
        public TextView Descricao;
        public ImageView imgDelete;
        

        public RecyclerViewHolderPergunta(@NonNull View itemView,final Context context) {
            super(itemView);
            Titulo = (TextView)itemView.findViewById(R.id.txtTitulo);
            Descricao = (TextView)itemView.findViewById(R.id.txtDescricao);
            imgDelete = (ImageView)itemView.findViewById(R.id.imgDelete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dados.size()>0){
                        PerguntaDto per = dados.get(getLayoutPosition());
                        MessageFinaliza("Deletar","Deseja deletar este registro!",v.getContext(),per.IdPergunta);
                    }

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dados.size()>0){
                        Intent it = new Intent(context, AcCadAltPergunta.class);
                        PerguntaDto per = dados.get(getLayoutPosition());
                        it.putExtra("Alterar",per);
                        ((AppCompatActivity)context).startActivityForResult(it,0);
                    }
                }
            });
        }

        private void MessageFinaliza(String titulo, String msg, final Context context, final Long idPergunta){
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
                    new PerguntaDal(v.getContext()).DeletaListaPergunta(idPergunta);
                    new PerguntaDal(v.getContext()).DeletaListaPersonalizada(idPergunta);
                    dialog.cancel();
                    RecyclerView recyclerView = (RecyclerView)((AppCompatActivity)context).findViewById(R.id.rcPergunda);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    ArrayList<PerguntaDto> lista = new PerguntaDal(context).RetornaPerguntas();
                    RecyclerPergunta perguntas = new RecyclerPergunta(lista,context);
                    recyclerView.setAdapter(perguntas);
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
