package RecyclerHistoricoDetalhado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app.devonaodevo.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;


public class DetalhadoItensAdapter extends ExpandableRecyclerViewAdapter<DetalhadoHeaderViewHolder,DetalhadoItensViewHolder> {


    public Context context;

    public DetalhadoItensAdapter(List<? extends ExpandableGroup> groups,Context context) {
        super(groups);
        this.context = context;
    }


    @Override
    public DetalhadoHeaderViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header_detalhado,parent,false);
        return new DetalhadoHeaderViewHolder(v);
    }

    @Override
    public DetalhadoItensViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_geral_detalhado,parent,false);
        return new DetalhadoItensViewHolder(v,this.context);
    }

    @Override
    public void onBindChildViewHolder(DetalhadoItensViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final DetalhadoItens product = (DetalhadoItens)group.getItems().get(childIndex);
        holder.bind(product);
    }

    @Override
    public void onBindGroupViewHolder(DetalhadoHeaderViewHolder holder, int flatPosition, ExpandableGroup group) {
        final DetalhadoHeader company = (DetalhadoHeader) group;
        holder.bind(company);
    }
}
