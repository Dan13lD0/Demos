package RecyclerViewGroups;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class PagarHeader extends ExpandableGroup<PagarItens> {
    public PagarHeader(String title, List<PagarItens> items) {
        super(title, items);
    }
}
