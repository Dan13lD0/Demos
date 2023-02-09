package RecyclerGroupPago;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.android.app.devonaodevo.AcListaGeralDetablado;
import com.android.app.devonaodevo.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import Dto.GrupoMesDto;

public class PagoItensViewHolder extends ChildViewHolder {
    GrupoMesDto click;

    public TextView data;
    public TextView tipo;

    public PagoItensViewHolder(View itemView, final Context context) {
        super(itemView);
        data=itemView.findViewById(R.id.txtTitulo);
        tipo=itemView.findViewById(R.id.txtTipo);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = new GrupoMesDto();
                click.MesAbreviado = data.getText().toString().trim().toUpperCase();
                click.MesExtenso = tipo.getText().toString().trim().toUpperCase();
                Intent it = new Intent(v.getContext(),AcListaGeralDetablado.class);
                it.putExtra("Alterar",click);
                ((Activity)context).startActivityForResult(it,0);
            }
        });
    }

    public void bind (PagoItens product){
        data.setText(product.data);
        tipo.setText(product.tipo);
    }

}
