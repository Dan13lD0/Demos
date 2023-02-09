package RecyclerGroupPago;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class PagoHeader extends ExpandableGroup<PagoItens> {
    public PagoHeader(String title, List items) {
        super(title, items);
    }
}
