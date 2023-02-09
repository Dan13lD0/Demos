package Recycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.pocketquestions.AcCadAltPergunta;
import com.android.pocketquestions.R;

import java.util.ArrayList;
import java.util.List;

import Dto.PerguntaListaDto;

public class RecyclerPerguntaLista extends RecyclerView.Adapter<RecyclerPerguntaLista.RecyclerViewHolderPergunta> {
    public static List<PerguntaListaDto> listaConfirma = new ArrayList<>();
    private List<PerguntaListaDto> dados;
    private Context context;

    public RecyclerPerguntaLista(List<PerguntaListaDto> dados, Context context) {
        listaConfirma.clear();
        this.dados = dados;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerPerguntaLista.RecyclerViewHolderPergunta onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.layout_recycler_pergunta_lista, viewGroup,false);
        RecyclerPerguntaLista.RecyclerViewHolderPergunta holderCategoria = new RecyclerPerguntaLista.RecyclerViewHolderPergunta(view,context);
        return holderCategoria;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerPerguntaLista.RecyclerViewHolderPergunta viewHolder, int i) {
        if ((dados != null) && (dados.size() > 0)) {
            PerguntaListaDto obj = dados.get(i);
            viewHolder.Titulo.setText(obj.Categoria);
            viewHolder.Descricao.setText(obj.Pergunta);
            viewHolder.Select.setChecked(obj.check);
            if(obj.check == true){
                listaConfirma.add(obj);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class RecyclerViewHolderPergunta extends RecyclerView.ViewHolder{
        public TextView Titulo;
        public TextView Descricao;
        public CheckBox Select;
        

        public RecyclerViewHolderPergunta(@NonNull View itemView,final Context context) {
            super(itemView);
            Titulo = (TextView)itemView.findViewById(R.id.txtTitulo);
            Descricao = (TextView)itemView.findViewById(R.id.txtDescricao);
            Select = (CheckBox)itemView.findViewById(R.id.ckSeleciona);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dados.size()>0){
                        PerguntaListaDto per = dados.get(getLayoutPosition());
                        if (per.check == true){
                            dados.get(getLayoutPosition()).check = false;
                            Select.setChecked(false);
                            listaConfirma.remove(per);
                        }
                        else{
                            dados.get(getLayoutPosition()).check = true;
                            Select.setChecked(true);
                            listaConfirma.add(per);
                        }

                    }
                }
            });
        }
    }
}
