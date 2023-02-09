package RecyclerGroupPago;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app.devonaodevo.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class PagoItensAdapter extends ExpandableRecyclerViewAdapter<PagoHeaderViewHolder,PagoItensViewHolder> {
    private Context context;
    public PagoItensAdapter(List<? extends ExpandableGroup> groups,Context context) {
        super(groups);
        this.context = context;
    }

    @Override
    public PagoHeaderViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header_pago,parent,false);
        return new PagoHeaderViewHolder(v);
    }

    @Override
    public PagoItensViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sub_header_pago,parent,false);
        return new PagoItensViewHolder(v,this.context);
    }

    @Override
    public void onBindChildViewHolder(PagoItensViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final PagoItens product = (PagoItens)group.getItems().get(childIndex);
        holder.bind(product);
    }

    @Override
    public void onBindGroupViewHolder(PagoHeaderViewHolder holder, int flatPosition, ExpandableGroup group) {
        final PagoHeader company = (PagoHeader) group;
        holder.bind(company);
    }
}
