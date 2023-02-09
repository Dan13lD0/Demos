package RecyclerViewGroups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app.devonaodevo.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class PagarItensAdapter extends ExpandableRecyclerViewAdapter<PagarHeaderViewHolder,PagarItensViewHolder> {

    private Context context;
    public PagarItensAdapter(List<? extends ExpandableGroup> groups,final Context context) {
        super(groups);
        this.context = context;
    }

    @Override
    public PagarHeaderViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hearer_pagar,parent,false);
        return new PagarHeaderViewHolder(v);
    }

    @Override
    public PagarItensViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_body_pagar,parent,false);
        return new PagarItensViewHolder(v,this.context);
    }

    @Override
    public void onBindChildViewHolder(PagarItensViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final PagarItens product = (PagarItens)group.getItems().get(childIndex);
        holder.bind(product);
    }

    @Override
    public void onBindGroupViewHolder(PagarHeaderViewHolder holder, int flatPosition, ExpandableGroup group) {
        final PagarHeader company = (PagarHeader) group;
        holder.bind(company);
    }
}
