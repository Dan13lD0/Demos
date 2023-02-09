package RecyclerHistoricoDetalhado;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class DetalhadoHeader extends ExpandableGroup<DetalhadoItens> {
    public DetalhadoHeader(String title, List<DetalhadoItens> items) {
        super(title, items);
    }
}
