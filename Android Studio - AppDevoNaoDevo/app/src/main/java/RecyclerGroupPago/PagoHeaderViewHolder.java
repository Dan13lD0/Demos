package RecyclerGroupPago;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

import com.android.app.devonaodevo.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class PagoHeaderViewHolder extends GroupViewHolder {

    private TextView sTextView;
    private ImageView arrow;

    public PagoHeaderViewHolder(View itemView) {
        super(itemView);
        sTextView = itemView.findViewById(R.id.txtTitulo);
        arrow = itemView.findViewById(R.id.arrow);
    }

    public void bind(PagoHeader company){
        sTextView.setText(company.getTitle());
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}
